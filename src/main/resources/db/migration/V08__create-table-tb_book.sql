CREATE TABLE tb_book (
    product_id VARCHAR(36) PRIMARY KEY COMMENT 'Primary key inherited from tb_product table',
    synopsis TEXT NOT NULL COMMENT 'Book synopsis or summary',
    page INT NOT NULL COMMENT 'Total number of pages',
    author VARCHAR(255) NOT NULL COMMENT 'Book author name',
    edition VARCHAR(255) NOT NULL COMMENT 'Book edition information',
    isbn VARCHAR(25) NOT NULL COMMENT 'International Standard Book Number (unique identifier)',
    category_book VARCHAR(255) NOT NULL COMMENT 'Book category or genre',
    height DECIMAL(5,2) NOT NULL COMMENT 'Book height in centimeters',
    width DECIMAL(5,2) NOT NULL COMMENT 'Book width in centimeters',
    length DECIMAL(5,2) NOT NULL COMMENT 'Book length in centimeters',
    weight DECIMAL(5,2) NOT NULL COMMENT 'Book weight in kilograms',
    publisher_date DATE NOT NULL COMMENT 'Book publishing date',

    CONSTRAINT uq_book_isbn UNIQUE (isbn),
    CONSTRAINT fk_book_product
        FOREIGN KEY (product_id)
        REFERENCES tb_product(product_id)
        ON DELETE CASCADE
) COMMENT = 'Stores detailed information about books, inheriting product_id from tb_product';

CREATE INDEX idx_book_author 
    ON tb_book(author) COMMENT 'Index to optimize queries filtering by author name';

CREATE INDEX idx_book_category 
    ON tb_book(category_book) COMMENT 'Index to optimize queries filtering by book category';

CREATE INDEX idx_book_publisher_date 
    ON tb_book(publisher_date) COMMENT 'Index to optimize queries filtering by publishing date';
