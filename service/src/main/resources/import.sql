INSERT INTO metro (id, name)
VALUES (1, 'Девяткино');
INSERT INTO metro (id, name)
VALUES (2, 'Гражданский проспект');
INSERT INTO metro (id, name)
VALUES (3, 'Академическая');
INSERT INTO metro (id, name)
VALUES (4, 'Политехническая');
INSERT INTO metro (id, name)
VALUES (5, 'Площадь Мужества');
INSERT INTO metro (id, name)
VALUES (6, 'Лесная');
INSERT INTO metro (id, name)
VALUES (7, 'Выборгская');
INSERT INTO metro (id, name)
VALUES (8, 'Площадь Ленина');
INSERT INTO metro (id, name)
VALUES (9, 'Чернышевская');
INSERT INTO metro (id, name)
VALUES (10, 'Площадь Восстания');
INSERT INTO metro (id, name)
VALUES (11, 'Владимирская');
INSERT INTO metro (id, name)
VALUES (12, 'Пушкинская');
INSERT INTO metro (id, name)
VALUES (13, 'Технологический институт');
INSERT INTO metro (id, name)
VALUES (14, 'Балтийская');
INSERT INTO metro (id, name)
VALUES (15, 'Нарвская');
INSERT INTO metro (id, name)
VALUES (16, 'Кировский завод');
INSERT INTO metro (id, name)
VALUES (17, 'Автово');
INSERT INTO metro (id, name)
VALUES (18, 'Ленинский проспект');
INSERT INTO metro (id, name)
VALUES (19, 'Проспект Ветеранов');
INSERT INTO metro (id, name)
VALUES (20, 'Парнас');
INSERT INTO metro (id, name)
VALUES (21, 'Проспект Просвещения');
INSERT INTO metro (id, name)
VALUES (22, 'Озерки');
INSERT INTO metro (id, name)
VALUES (23, 'Удельная');
INSERT INTO metro (id, name)
VALUES (24, 'Пионерская');
INSERT INTO metro (id, name)
VALUES (25, 'Черная речка');
INSERT INTO metro (id, name)
VALUES (26, 'Петроградская');
INSERT INTO metro (id, name)
VALUES (27, 'Горьковская');
INSERT INTO metro (id, name)
VALUES (28, 'Невский проспект');
INSERT INTO metro (id, name)
VALUES (29, 'Сенная площадь');
INSERT INTO metro (id, name)
VALUES (30, 'Фрунзенская');
INSERT INTO metro (id, name)
VALUES (31, 'Московские ворота');
INSERT INTO metro (id, name)
VALUES (32, 'Электросила');
INSERT INTO metro (id, name)
VALUES (33, 'Парк Победы');
INSERT INTO metro (id, name)
VALUES (34, 'Московская');
INSERT INTO metro (id, name)
VALUES (35, 'Звездная');
INSERT INTO metro (id, name)
VALUES (36, 'Купчино');
INSERT INTO metro (id, name)
VALUES (37, 'Приморская');
INSERT INTO metro (id, name)
VALUES (38, 'Василеостровская');
INSERT INTO metro (id, name)
VALUES (39, 'Гостиный двор');
INSERT INTO metro (id, name)
VALUES (40, 'Маяковская');
INSERT INTO metro (id, name)
VALUES (41, 'Площадь Александра Невского');
INSERT INTO metro (id, name)
VALUES (42, 'Елизаровская');
INSERT INTO metro (id, name)
VALUES (43, 'Ломоносовская');
INSERT INTO metro (id, name)
VALUES (44, 'Пролетарская');
INSERT INTO metro (id, name)
VALUES (45, 'Обухово');
INSERT INTO metro (id, name)
VALUES (46, 'Рыбацкое');
INSERT INTO metro (id, name)
VALUES (47, 'Комендантский проспект');
INSERT INTO metro (id, name)
VALUES (48, 'Старая Деревня');
INSERT INTO metro (id, name)
VALUES (49, 'Крестовский остров');
INSERT INTO metro (id, name)
VALUES (50, 'Чкаловская');
INSERT INTO metro (id, name)
VALUES (51, 'Спортивная');
INSERT INTO metro (id, name)
VALUES (52, 'Садовая');
INSERT INTO metro (id, name)
VALUES (53, 'Достоевская');
INSERT INTO metro (id, name)
VALUES (54, 'Лиговский проспект');
INSERT INTO metro (id, name)
VALUES (55, 'Новочеркасская');
INSERT INTO metro (id, name)
VALUES (56, 'Ладожская');
INSERT INTO metro (id, name)
VALUES (57, 'Проспект Большевиков');
INSERT INTO metro (id, name)
VALUES (58, 'Улица Дыбенко');
INSERT INTO metro (id, name)
VALUES (59, 'Волковская');
INSERT INTO metro (id, name)
VALUES (60, 'Звенигородская');
INSERT INTO metro (id, name)
VALUES (61, 'Спасская');
INSERT INTO metro (id, name)
VALUES (62, 'Обводный канал');
INSERT INTO metro (id, name)
VALUES (63, 'Адмиралтейская');
INSERT INTO metro (id, name)
VALUES (64, 'Международная');
INSERT INTO metro (id, name)
VALUES (65, 'Бухарестская');
INSERT INTO metro (id, name)
VALUES (66, 'Беговая');
INSERT INTO metro (id, name)
VALUES (67, 'Зенит');
INSERT INTO metro (id, name)
VALUES (68, 'Проспект Славы');
INSERT INTO metro (id, name)
VALUES (69, 'Дунайская');
INSERT INTO metro (id, name)
VALUES (70, 'Шушары');
INSERT INTO metro (id, name)
VALUES (71, 'Горный институт');

INSERT INTO category (id, name)
VALUES (1, 'Недвижимость');
INSERT INTO category(id, name)
VALUES (2, 'Транспорт');
INSERT INTO category (id, name)
VALUES (3, 'Детские товары');
INSERT INTO category(id, name)
VALUES (4, 'Мебель');
INSERT INTO category (id, name)
VALUES (5, 'Одежда');
INSERT INTO category(id, name)
VALUES (6, 'Техника');

INSERT INTO sub_category (id, name, category_id)
VALUES (1, 'Квартира', 1);
INSERT INTO sub_category (id, name, category_id)
VALUES (2, 'Гараж', 1);
INSERT INTO sub_category (id, name, category_id)
VALUES (3, 'Дом', 1);
INSERT INTO sub_category (id, name, category_id)
VALUES (4, 'Аппаратменты', 1);
INSERT INTO sub_category (id, name, category_id)
VALUES (5, 'Машина', 2);
INSERT INTO sub_category (id, name, category_id)
VALUES (6, 'Мотоцикл', 2);
INSERT INTO sub_category (id, name, category_id)
VALUES (7, 'Самокат', 2);
INSERT INTO sub_category (id, name, category_id)
VALUES (8, 'Лодка', 2);
INSERT INTO sub_category (id, name, category_id)
VALUES (9, 'Игрушки', 3);
INSERT INTO sub_category (id, name, category_id)
VALUES (10, 'Коляски', 3);
INSERT INTO sub_category (id, name, category_id)
VALUES (11, 'Шкафы', 4);
INSERT INTO sub_category (id, name, category_id)
VALUES (12, 'Кровати', 4);
INSERT INTO sub_category (id, name, category_id)
VALUES (13, 'Диван', 4);
INSERT INTO sub_category (id, name, category_id)
VALUES (14, 'Стол', 4);
INSERT INTO sub_category (id, name, category_id)
VALUES (15, 'Стул', 4);
INSERT INTO sub_category (id, name, category_id)
VALUES (16, 'Детская', 5);
INSERT INTO sub_category (id, name, category_id)
VALUES (17, 'Зимняя', 5);
INSERT INTO sub_category (id, name, category_id)
VALUES (18, 'Обувь', 5);
INSERT INTO sub_category (id, name, category_id)
VALUES (19, 'Остальное', 5);
INSERT INTO sub_category (id, name, category_id)
VALUES (20, 'Телефоны', 6);
INSERT INTO sub_category (id, name, category_id)
VALUES (21, 'Ноутбуки', 6);
INSERT INTO sub_category (id, name, category_id)
VALUES (22, 'Бытовая техника', 6);
INSERT INTO sub_category (id, name, category_id)
VALUES (23, 'Остальное', 6);