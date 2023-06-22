package exercise.article;

import exercise.worker.WorkerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class WorkerImplTest {

    private WorkerImpl subject;
    @Mock
    private Library library;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        subject = new WorkerImpl(library);
    }

    @Test
    public void ArticleWithoutDate() throws Exception {
        List<Article> newArticle = new ArrayList<>();
        newArticle.add(new Article(
                "Топ дурных советов",
                "Как разобраться в unit тестах и не сойти с ума",
                "Вася Пупкин",
                null));

        List<Article> articles = subject.prepareArticles(newArticle);
        assertNotNull(articles.get(0).getCreationDate());

    }

    @Test
    public void ArticleWithoutTitle() throws Exception {
        List<Article> newArticle = new ArrayList<>();
        newArticle.add(new Article(
                " ",
                "Как разобраться в unit тестах и не сойти с ума",
                "Вася Пупкин",
                LocalDate.of(2023, 06, 20)));

        List<Article> articles = subject.prepareArticles(newArticle);
        assertFalse(articles.contains(newArticle.get(0)));
    }

    @Test
    public void ArticleWithoutContent() throws Exception {
        List<Article> newArticle = new ArrayList<>();
        newArticle.add(new Article(
                "Топ дурных советов",
                " ",
                "Вася Пупкин",
                LocalDate.of(2023, 06, 20)));

        List<Article> articles = subject.prepareArticles(newArticle);
        assertFalse(articles.contains(newArticle.get(0)));
    }

    @Test
    public void ArticleWithoutAuthor() throws Exception {
        List<Article> newArticle = new ArrayList<>();
        newArticle.add(new Article(
                "Топ дурных советов",
                "Как разобраться в unit тестах и не сойти с ума",
                " ",
                LocalDate.of(2023, 06, 20)));

        List<Article> articles = subject.prepareArticles(newArticle);
        assertFalse(articles.contains(newArticle.get(0)));
    }

    @Test
    public void ShouldAddNewArticles() {
        List<Article> newArticle = new ArrayList<>();
        newArticle.add(new Article(
                "Путь к успеху",
                "Боль, страдания и джава кор приведут тебя к успеху",
                "Вениамин Свитерочкин ",
                LocalDate.of(2023, 05, 07)));
        newArticle.add(new Article(
                "Путь к успеху 2.0",
                "Гнев, торг, принятие, окей гугл: что такоею юнит тесты",
                "Вениамин Свитерочкин ",
                LocalDate.of(2023, 06, 10)));
        List<Article> articles = subject.prepareArticles(newArticle);
        assertEquals(2, articles.size());
        assertEquals("Путь к успеху", articles.get(0).getTitle());
        assertEquals("Путь к успеху 2.0", articles.get(1).getTitle());
    }

    @Test
    public void GetCatalogWithoutNewArticles() {
        List<Article> newArticle = new ArrayList<>();
        List<Article> articles = subject.prepareArticles(newArticle);
        assertEquals(0, articles.size());
    }


    @Test
    public void ShouldGetCatalog() {

        List<String> titles = List.of("Путь к успеху", "Путь к успеху 2.0", "Путь к успеху 3.0", "Путь к успеху 4.0");
        Mockito.when(library.getAllTitles()).thenReturn(titles);

        StringBuilder sb = new StringBuilder("Список доступных статей:\n");
        titles.stream()
                .sorted(String::compareTo)
                .forEachOrdered(title -> sb.append("    ").append(title).append("\n"));
        String expectedResult = sb.toString();

        String result = subject.getCatalog();
        assertEquals(expectedResult, result);

    }
    @Test
    public void CheckingForDuplicateArticles (){
        List<Article> newArticle = new ArrayList<>();
        newArticle.add(new Article(
                "Путь к успеху",
                "Боль, страдания и джава кор приведут тебя к успеху",
                "Вениамин Свитерочкин ",
                LocalDate.of(2023, 05, 07)));
        newArticle.add(new Article(
                "Путь к успеху",
                "Боль, страдания и джава кор приведут тебя к успеху",
                "Вениамин Свитерочкин ",
                LocalDate.of(2023, 05, 07)));
        List<Article> articles = subject.prepareArticles(newArticle);
        assertEquals(1, articles.size());


    }
}

