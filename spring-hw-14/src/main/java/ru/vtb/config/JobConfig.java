package ru.vtb.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.data.domain.Sort.Direction;
import ru.vtb.mapper.AuthorMapper;
import ru.vtb.mapper.BookMapper;
import ru.vtb.mapper.CommentMapper;
import ru.vtb.mapper.GenreMapper;
import ru.vtb.model.sql.Author;
import ru.vtb.repository.nosql.AuthorRepository;
import ru.vtb.repository.nosql.BookRepository;
import ru.vtb.repository.nosql.CommentRepository;
import ru.vtb.repository.nosql.GenreRepository;
import ru.vtb.repository.sql.AuthorJpaRepository;
import ru.vtb.repository.sql.BookJpaRepository;
import ru.vtb.repository.sql.CommentJpaRepository;
import ru.vtb.repository.sql.GenreJpaRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class JobConfig {

    private static final int CHUNK_SIZE = 1;
    public static final String DB_MIGRATION_JOB = "dbMigrationJob";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final ItemReadListener<Author> itemReadListener = new ItemReadListener<>() {
        @Override
        public void beforeRead() {
            log.info("Начало чтения");
        }
        @Override
        public void afterRead(Author author) {
            log.info("Конец чтения");
        }
        @Override
        public void onReadError(Exception e) {
            log.info("Ошибка чтения");
        }
    };

    private final ItemWriteListener<ru.vtb.model.nosql.Author> itemWriteListener = new ItemWriteListener<>() {
        @Override
        public void beforeWrite(List<? extends ru.vtb.model.nosql.Author> list) {
            log.info("Начало записи");
        }
        @Override
        public void afterWrite(List<? extends ru.vtb.model.nosql.Author> list) {
            log.info("Конец записи");
        }
        @Override
        public void onWriteError(Exception e, List<? extends ru.vtb.model.nosql.Author> list) {
            log.info("Ошибка записи");
        }
    };

    private final ItemProcessListener<Author, ru.vtb.model.nosql.Author> itemProcessListener = new ItemProcessListener<>() {
        @Override
        public void beforeProcess(Author o) {
            log.info("Начало обработки");
        }
        @Override
        public void afterProcess(Author o, ru.vtb.model.nosql.Author o2) {
            log.info("Конец обработки");
        }
        @Override
        public void onProcessError(Author o, Exception e) {
            log.info("Ошбка обработки");
        }
    };

    private final ChunkListener chunkListener = new ChunkListener() {
        @Override
        public void beforeChunk(ChunkContext chunkContext) {
            log.info("Начало пачки");
        }
        @Override
        public void afterChunk(ChunkContext chunkContext) {
            log.info("Конец пачки");
        }
        @Override
        public void afterChunkError(ChunkContext chunkContext) {
            log.info("Ошибка пачки");
        }
    };

    private final JobExecutionListener jobExecutionListener = new JobExecutionListener() {
        @Override
        public void beforeJob(JobExecution jobExecution) {
            log.info("Начало job");
        }
        @Override
        public void afterJob(JobExecution jobExecution) {
            log.info("Конец job");
        }
    };

    @Bean
    public RepositoryItemReader<ru.vtb.model.sql.Author> authorReader(AuthorJpaRepository authorRepository) {
        val authorRepositoryItemReader = new RepositoryItemReader<ru.vtb.model.sql.Author>();
        authorRepositoryItemReader.setRepository(authorRepository);
        authorRepositoryItemReader.setMethodName("findAll");
        Map<String, Direction> sorts = new HashMap<>();
        sorts.put("id", Direction.ASC);
        authorRepositoryItemReader.setSort(sorts);
        return authorRepositoryItemReader;
    }

    @Bean
    public ItemProcessor<ru.vtb.model.sql.Author, ru.vtb.model.nosql.Author> authorProcessor(AuthorMapper authorMapper) {
        return authorMapper::toDocumentEntity;
    }

    @Bean
    public RepositoryItemWriter<ru.vtb.model.nosql.Author> authorWriter(ru.vtb.repository.nosql.AuthorRepository authorRepository) {
        RepositoryItemWriter<ru.vtb.model.nosql.Author> itemWriter = new RepositoryItemWriter<>();
        itemWriter.setRepository(authorRepository);
        itemWriter.setMethodName("save");
        return itemWriter;
    }

    @Bean
    public RepositoryItemReader<ru.vtb.model.sql.Genre> genreReader(GenreJpaRepository genreRepository) {
        val genreRepositoryItemReader = new RepositoryItemReader<ru.vtb.model.sql.Genre>();
        genreRepositoryItemReader.setRepository(genreRepository);
        genreRepositoryItemReader.setMethodName("findAll");
        Map<String, Direction> sorts = new HashMap<>();
        sorts.put("code", Direction.ASC);
        genreRepositoryItemReader.setSort(sorts);
        return genreRepositoryItemReader;
    }

    @Bean
    public ItemProcessor<ru.vtb.model.sql.Genre, ru.vtb.model.nosql.Genre> genreProcessor(GenreMapper genreMapper) {
        return genreMapper::toDocumentEntity;
    }

    @Bean
    public RepositoryItemWriter<ru.vtb.model.nosql.Genre> genreWriter(ru.vtb.repository.nosql.GenreRepository genreRepository) {
        RepositoryItemWriter<ru.vtb.model.nosql.Genre> itemWriter = new RepositoryItemWriter<>();
        itemWriter.setRepository(genreRepository);
        itemWriter.setMethodName("save");
        return itemWriter;
    }

    @Bean
    public RepositoryItemReader<ru.vtb.model.sql.Book> bookReader(BookJpaRepository bookRepository) {
        val bookRepositoryItemReader = new RepositoryItemReader<ru.vtb.model.sql.Book>();
        bookRepositoryItemReader.setRepository(bookRepository);
        bookRepositoryItemReader.setMethodName("findAll");
        Map<String, Direction> sorts = new HashMap<>();
        sorts.put("id", Direction.ASC);
        bookRepositoryItemReader.setSort(sorts);
        return bookRepositoryItemReader;
    }

    @Bean
    public ItemProcessor<ru.vtb.model.sql.Book, ru.vtb.model.nosql.Book> bookProcessor(BookMapper bookMapper) {
        return bookMapper::toDocumentEntity;
    }

    @Bean
    public RepositoryItemWriter<ru.vtb.model.nosql.Book> bookWriter(ru.vtb.repository.nosql.BookRepository bookRepository) {
        RepositoryItemWriter<ru.vtb.model.nosql.Book> itemWriter = new RepositoryItemWriter<>();
        itemWriter.setRepository(bookRepository);
        itemWriter.setMethodName("save");
        return itemWriter;
    }

    @Bean
    public RepositoryItemReader<ru.vtb.model.sql.Comment> commentReader(CommentJpaRepository commentRepository) {
        val commentRepositoryItemReader = new RepositoryItemReader<ru.vtb.model.sql.Comment>();
        commentRepositoryItemReader.setRepository(commentRepository);
        commentRepositoryItemReader.setMethodName("findAll");
        Map<String, Direction> sorts = new HashMap<>();
        sorts.put("id", Direction.ASC);
        commentRepositoryItemReader.setSort(sorts);
        return commentRepositoryItemReader;
    }

    @Bean
    public ItemProcessor<ru.vtb.model.sql.Comment, ru.vtb.model.nosql.Comment> commentProcessor(CommentMapper commentMapper) {
        return commentMapper::toDocumentEntity;
    }

    @Bean
    public RepositoryItemWriter<ru.vtb.model.nosql.Comment> commentWriter(ru.vtb.repository.nosql.CommentRepository commentRepository) {
        RepositoryItemWriter<ru.vtb.model.nosql.Comment> itemWriter = new RepositoryItemWriter<>();
        itemWriter.setRepository(commentRepository);
        itemWriter.setMethodName("save");
        return itemWriter;
    }

    @Bean
    public Step authorStep(RepositoryItemReader<ru.vtb.model.sql.Author> authorReader,
                           ItemProcessor<ru.vtb.model.sql.Author, ru.vtb.model.nosql.Author> authorProcessor,
                           RepositoryItemWriter<ru.vtb.model.nosql.Author> authorWriter) {
        return this.stepBuilderFactory.get("authorStep")
                .<ru.vtb.model.sql.Author, ru.vtb.model.nosql.Author>chunk(CHUNK_SIZE)
                .reader(authorReader)
                .processor(authorProcessor)
                .writer(authorWriter)
                .listener(itemReadListener)
                .listener(itemWriteListener)
                .listener(itemProcessListener)
                .listener(chunkListener)
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .build();
    }

    @Bean
    public Step genreStep(RepositoryItemReader<ru.vtb.model.sql.Genre> genreReader,
                          ItemProcessor<ru.vtb.model.sql.Genre, ru.vtb.model.nosql.Genre> genreProcessor,
                          RepositoryItemWriter<ru.vtb.model.nosql.Genre> genreWriter) {
        return this.stepBuilderFactory.get("genreStep")
                .<ru.vtb.model.sql.Genre, ru.vtb.model.nosql.Genre>chunk(CHUNK_SIZE)
                .reader(genreReader)
                .processor(genreProcessor)
                .writer(genreWriter)
                .listener(itemReadListener)
                .listener(itemWriteListener)
                .listener(itemProcessListener)
                .listener(chunkListener)
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .build();
    }

    @Bean
    public Step bookStep(RepositoryItemReader<ru.vtb.model.sql.Book> bookReader,
                            ItemProcessor<ru.vtb.model.sql.Book, ru.vtb.model.nosql.Book> bookProcessor,
                            RepositoryItemWriter<ru.vtb.model.nosql.Book> bookWriter) {

        return this.stepBuilderFactory.get("bookStep")
                .<ru.vtb.model.sql.Book, ru.vtb.model.nosql.Book>chunk(CHUNK_SIZE)
                .reader(bookReader)
                .processor(bookProcessor)
                .writer(bookWriter)
                .listener(itemReadListener)
                .listener(itemWriteListener)
                .listener(itemProcessListener)
                .listener(chunkListener)
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .build();
    }

    @Bean
    public Step commentStep(RepositoryItemReader<ru.vtb.model.sql.Comment> commentReader,
                          ItemProcessor<ru.vtb.model.sql.Comment, ru.vtb.model.nosql.Comment> commentProcessor,
                          RepositoryItemWriter<ru.vtb.model.nosql.Comment> commentWriter) {
        return this.stepBuilderFactory.get("commentStep")
                .<ru.vtb.model.sql.Comment, ru.vtb.model.nosql.Comment>chunk(CHUNK_SIZE)
                .reader(commentReader)
                .processor(commentProcessor)
                .writer(commentWriter)
                .listener(itemReadListener)
                .listener(itemWriteListener)
                .listener(itemProcessListener)
                .listener(chunkListener)
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .build();
    }

    @Bean
    public Step clearDbStep(Tasklet tasklet) {
        return this.stepBuilderFactory.get("clearDbStep")
                .tasklet(tasklet)
                .build();
    }

    @Bean
    public Tasklet tasklet(AuthorRepository authorRepository,
                           GenreRepository genreRepository,
                           CommentRepository commentRepository,
                           BookRepository bookRepository) {
        return (contribution, chunkContext) -> {
            authorRepository.deleteAll();
            genreRepository.deleteAll();
            bookRepository.deleteAll();
            commentRepository.deleteAll();
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Job dbMigrationJob(
            Step clearDbStep
            , Step authorStep
            , Step genreStep
            , Step bookStep
            , Step commentStep) {
        return jobBuilderFactory.get(DB_MIGRATION_JOB)
                .incrementer(new RunIdIncrementer())
                .start(clearDbStep)
                .next(authorStep)
                .next(genreStep)
                .next(bookStep)
                .next(commentStep)
                .listener(jobExecutionListener)
                .build();
    }
}
