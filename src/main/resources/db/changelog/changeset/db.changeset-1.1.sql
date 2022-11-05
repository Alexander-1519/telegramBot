insert into Quizes (id, question, category) VALUES (2, 'Java is short for "JavaScript".', 'QUIZ_BASIC');
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('True', false, 2);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('False', true, 2);

insert into Quizes (id, question, category) VALUES (3, 'How do you insert COMMENTS in Java code?', 'QUIZ_BASIC');
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('// This is a comment', true, 3);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('# This is a comment', false, 3);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('/* This is a comment', false, 3);

insert into Quizes (id, question, category) VALUES (4, 'Which data type is used to create a variable that should store text?', 'QUIZ_OOP');
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('myString', false, 4);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('Txt', false, 4);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('String', true, 4);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('string', false, 4);

insert into Quizes (id, question, category) VALUES (5, 'How do you create a variable with the numeric value 5?', 'QUIZ_BASIC');
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('num x = 5;', false, 5);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('int x = 5;', true, 5);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('float x = 5;', false, 5);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('x = 5', false, 5);

insert into Quizes (id, question, category) VALUES (6, 'How do you create a variable with the floating number 2.8?', 'QUIZ_BASIC');
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('float x = 2.8f;', true, 6);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('int x = 2.8f;', false, 6);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('byte x = 2.8f;', false, 6);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('x = 2.8f;', false, 6);

insert into Quizes (id, question, category) VALUES (7, 'Which method can be used to find the length of a string?', 'QUIZ_OOP');
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('getLength()', false, 7);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('length()', true, 7);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('len()', false, 7);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('getSize()', false, 7);

insert into Quizes (id, question, category) VALUES (8, 'Which operator is used to add together two values?', 'QUIZ_BASIC');
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('&', false, 8);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('*', false, 8);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('+', true, 8);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('-', false, 8);

insert into Quizes (id, question, category) VALUES (9, 'The value of a string variable can be surrounded by single quotes.', 'QUIZ_OOP');
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('True', false, 9);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('False', true, 9);

insert into Quizes (id, question, category) VALUES (10, 'Which method can be used to return a string in upper case letters?', 'QUIZ_OOP');
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('upperCase()', false, 10);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('toUpperCase()', true, 10);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('tuc()', false, 10);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('touppercase()', false, 10);

insert into Quizes (id, question, category) VALUES (11, 'Which operator can be used to compare two values?', 'QUIZ_BASIC');
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('==', true, 11);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('=', false, 11);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('<>', false, 11);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('><', false, 11);

insert into Quizes (id, question, category) VALUES (12, 'To declare an array in Java, define the variable type with:', 'QUIZ_BASIC');
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('()', false, 12);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('{}', false, 12);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('[]', true, 12);

insert into Quizes (id, question, category) VALUES (13, 'Array indexes start with:', 'QUIZ_BASIC');
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('0', true, 13);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('1', false, 13);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('-1', false, 13);

insert into Quizes (id, question, category) VALUES (14, 'How do you create a method in Java?', 'QUIZ_BASIC');
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('methodName[]', false, 14);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('methodName.', false, 14);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('(methodName)', false, 14);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('methodName()', true, 14);

insert into Quizes (id, question, category) VALUES (15, 'How do you call a method in Java?', 'QUIZ_BASIC');
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('(methodName);', false, 15);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('methodName[];', false, 15);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('methodName();', true, 15);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('methodName;', false, 15);

insert into Quizes (id, question, category) VALUES (16, 'Which keyword is used to create a class in Java?', 'QUIZ_OOP');
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('MyClass', false, 16);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('class', true, 16);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('class();', false, 16);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('className', false, 16);

insert into Quizes (id, question, category) VALUES (17, 'What is the correct way to create an object called myObj of MyClass?', 'QUIZ_OOP');
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('class myObj = new MyClass();', false, 17);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('class myObj = new MyObj();', false, 17);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('new myObj = MyClass();', false, 17);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES (' MyClass myObj = new MyClass();', true, 17);

insert into Quizes (id, question, category) VALUES (18, 'In Java, it is possible to inherit attributes and methods from one class to another.', 'QUIZ_OOP');
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('False', false, 18);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('True', true, 18);

insert into Quizes (id, question, category) VALUES (19, 'Which method can be used to find the highest value of x and y?', 'QUIZ_BASIC');
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('Math.max(x,y);', true, 19);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('Math.maximum(x,y);', false, 19);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('Math.largest(x,y);', false, 19);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('Math.maxNum(x,y);', false, 19);

insert into Quizes (id, question, category) VALUES (20, 'Which operator is used to multiply numbers?', 'QUIZ_BASIC');
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('%', false, 20);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('*', true, 20);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('-', false, 20);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('/', false, 20);

insert into Quizes (id, question, category) VALUES (21, 'Which keyword is used to import a package from the Java API library?', 'QUIZ_BASIC');
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('import', true, 21);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('package', false, 21);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('lib', false, 21);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('getlib', false, 21);

insert into Quizes (id, question, category) VALUES (22, 'How do you start writing an if statement in Java?', 'QUIZ_BASIC');
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('if(x > y)', true, 22);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('if x > y:', false, 22);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('if x > y then:', false, 22);

insert into Quizes (id, question, category) VALUES (23, 'How do you start writing a while loop in Java?', 'QUIZ_BASIC');
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('x > y while {', false, 23);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('while (x > y)', true, 23);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('while x > y {', false, 23);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('while x > y:', false, 23);

insert into Quizes (id, question, category) VALUES (24, 'Which keyword is used to return a value inside a method?', 'QUIZ_BASIC');
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('return', true, 24);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('break', false, 24);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('continue', false, 24);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('void', false, 24);

insert into Quizes (id, question, category) VALUES (25, 'Which statement is used to stop a loop?', 'QUIZ_BASIC');
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('return', false, 25);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('exit', false, 25);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('break', true, 25);
insert into Quiz_Variants (answer, is_answer, quiz_id) VALUES ('stop', false, 25);

