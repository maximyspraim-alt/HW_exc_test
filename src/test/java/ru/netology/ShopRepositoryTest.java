package ru.netology;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShopRepositoryTest {
    private ShopRepository repository;

    @BeforeEach
    public void setUp() {
        repository = new ShopRepository();
    }

    @Test
    public void shouldAddProducts() {
        Product product1 = new Product(1, "Телефон", 50000);
        Product product2 = new Product(2, "Ноутбук", 80000);

        repository.add(product1);
        repository.add(product2);

        Product[] expected = {product1, product2};
        Product[] actual = repository.findAll();

        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldRemoveExistingProductById() {
        Product product1 = new Product(1, "Телефон", 50000);
        Product product2 = new Product(2, "Ноутбук", 80000);
        Product product3 = new Product(3, "Планшет", 30000);

        repository.add(product1);
        repository.add(product2);
        repository.add(product3);

        // Удаляем товар с ID = 2
        repository.removeById(2);

        Product[] expected = {product1, product3};
        Product[] actual = repository.findAll();

        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldRemoveFirstProductById() {
        Product product1 = new Product(1, "Телефон", 50000);
        Product product2 = new Product(2, "Ноутбук", 80000);
        Product product3 = new Product(3, "Планшет", 30000);

        repository.add(product1);
        repository.add(product2);
        repository.add(product3);

        // Удаляем первый товар
        repository.removeById(1);

        Product[] expected = {product2, product3};
        Product[] actual = repository.findAll();

        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldRemoveLastProductById() {
        Product product1 = new Product(1, "Телефон", 50000);
        Product product2 = new Product(2, "Ноутбук", 80000);
        Product product3 = new Product(3, "Планшет", 30000);

        repository.add(product1);
        repository.add(product2);
        repository.add(product3);

        // Удаляем последний товар
        repository.removeById(3);

        Product[] expected = {product1, product2};
        Product[] actual = repository.findAll();

        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldRemoveProductWhenOnlyOneProductExists() {
        Product product1 = new Product(1, "Телефон", 50000);

        repository.add(product1);

        // Удаляем единственный товар
        repository.removeById(1);

        Product[] expected = {};
        Product[] actual = repository.findAll();

        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldThrowNotFoundExceptionWhenRemovingNonExistentProduct() {
        Product product1 = new Product(1, "Телефон", 50000);
        Product product2 = new Product(2, "Ноутбук", 80000);

        repository.add(product1);
        repository.add(product2);

        // Пытаемся удалить несуществующий товар с ID = 999
        Assertions.assertThrows(
                NotFoundException.class,
                () -> repository.removeById(999),
                "Expected removeById to throw NotFoundException"
        );
    }

    @Test
    public void shouldThrowNotFoundExceptionWithCorrectMessage() {
        Product product1 = new Product(1, "Телефон", 50000);
        Product product2 = new Product(2, "Ноутбук", 80000);

        repository.add(product1);
        repository.add(product2);

        // Пытаемся удалить несуществующий товар и проверяем сообщение исключения
        NotFoundException exception = Assertions.assertThrows(
                NotFoundException.class,
                () -> repository.removeById(999)
        );

        Assertions.assertEquals(
                "Element with id: 999 not found",
                exception.getMessage()
        );
    }

    @Test
    public void shouldThrowNotFoundExceptionWhenRemovingFromEmptyRepository() {
        // Репозиторий пустой, пытаемся удалить товар с ID = 1
        Assertions.assertThrows(
                NotFoundException.class,
                () -> repository.removeById(1)
        );
    }

    @Test
    public void shouldFindProductById() {
        Product product1 = new Product(1, "Телефон", 50000);
        Product product2 = new Product(2, "Ноутбук", 80000);

        repository.add(product1);
        repository.add(product2);

        Product found = repository.findById(2);
        Assertions.assertEquals(product2, found);
    }

    @Test
    public void shouldReturnNullWhenProductNotFound() {
        Product product1 = new Product(1, "Телефон", 50000);
        Product product2 = new Product(2, "Ноутбук", 80000);

        repository.add(product1);
        repository.add(product2);

        Product found = repository.findById(999);
        Assertions.assertNull(found);
    }
}