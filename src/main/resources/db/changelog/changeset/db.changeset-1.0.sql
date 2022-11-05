CREATE TABLE Questions(
    id                  BIGSERIAL PRIMARY KEY,
    name                VARCHAR(500) NOT NULL,
    answer              VARCHAR(2500) NOT NULL,
    category            VARCHAR(255) NOT NULL
);

CREATE TABLE Quizes(
    id                  BIGSERIAL PRIMARY KEY,
    question            VARCHAR(2500) NOT NULL,
    category            VARCHAR(255) NOT NULL
);

CREATE TABLE Quiz_Variants(
    id                  BIGSERIAL PRIMARY KEY,
    answer              VARCHAR(2500) NOT NULL,
    is_answer           BOOLEAN DEFAULT FALSE,
    quiz_id             BIGINT REFERENCES Quizes(id) NOT NULL
);

insert into Questions (name, answer, category) values('Какая разница между fail-fast и fail-safe?', CONCAT(
                                                      'Итератор коллекции CopyOnWriteArrayList ',
                                                      'и итератор представления keySet коллекции ConcurrentHashMap',
                                                      ' являются примерами итераторов fail-safe.'),
                                                      'QUESTION_COLLECTIONS');

insert into Questions (name, answer, category) values('Какие существуют модификаторы доступа?', 'private (приватный): члены класса доступны только внутри класса. Для обозначения используется служебное слово private.

default, package-private, package level (доступ на уровне пакета): видимость класса/членов класса только внутри пакета. Является модификатором доступа по умолчанию - специальное обозначение не требуется.

protected (защищённый): члены класса доступны внутри пакета и в наследниках. Для обозначения используется служебное слово protected.

public (публичный): класс/члены класса доступны всем. Для обозначения используется служебное слово public.

Последовательность модификаторов по возрастанию уровня закрытости: public, protected, default, private.

Во время наследования возможно изменения модификаторов доступа в сторону большей видимости (для поддержания соответствия принципу подстановки Барбары Лисков).',
                                                      'QUESTION_BASIC');

insert into Quizes (id, question, category) VALUES (1, 'What is a correct syntax to output "Hello World" in Java?', 'QUIZ_BASIC');
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('echo("Hello World");', false, 1);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('Console.WriteLine("Hello World");', false, 1);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('System.out.println("Hello World");', true, 1);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('print("Hello World");', false, 1);