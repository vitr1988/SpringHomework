package ru.otus.spring.integration;

import lombok.SneakyThrows;
import org.jsoup.select.Elements;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;
import ru.otus.spring.integration.domain.Content;
import ru.otus.spring.integration.domain.Information;
import ru.otus.spring.integration.messaging.News;
import ru.otus.spring.integration.util.ContentExtractor;

import java.io.IOException;
import java.time.Duration;
import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;


@IntegrationComponentScan
@SuppressWarnings({"resource", "Duplicates", "InfiniteLoopStatement"})
@ComponentScan
@Configuration
@EnableIntegration
public class App {

    private static Collection<Content> lastContent;

    @Bean
    public QueueChannel rbcChannel() {
        return MessageChannels.queue(10).get();
    }

    @Bean
    public PublishSubscribeChannel audienceChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedRate(Duration.ofSeconds(1)).maxMessagesPerPoll(5).get() ;
    }

    @Bean
    public IntegrationFlow newsFlow() {
        return IntegrationFlows.from("rbcChannel")
                .split()
                .handle("newsService", "process")
                .aggregate()
                .channel("audienceChannel")
                .get();
    }

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(App.class);
        News news = ctx.getBean(News.class);

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            @SneakyThrows
            public void run() {
                Collection<Content> items = fetchNewsContent();
                if (lastContent == null || !lastContent.containsAll(items)) {
                    System.out.println("Последние новости с главной страницы РБК:\n" +
                            items.stream().map(Content::getTitle).collect(Collectors.joining("\n")));
                    Collection<Information> information = news.gather(items);
                    System.out.println("Содержимое статей:\n" +
                            information.stream().map(Information::getValue).collect(Collectors.joining("\n")));
                    lastContent = items;
                }
            }
        }, 0L, Duration.ofHours(1).toMillis());
    }

    private static Collection<Content> fetchNewsContent() throws IOException {
        Elements newsLinks = ContentExtractor.getContentBy("https://www.rbc.ru", "main__feed__link");
        return newsLinks.stream().map(element -> new Content(element.text(), element.attr("href"))).collect(Collectors.toList());
    }
}
