INSERT INTO metro (id, name) VALUES(1,  'Девяткино');
INSERT INTO metro (id, name) VALUES(2,  'Гражданский проспект');
INSERT INTO metro (id, name) VALUES(3,  'Академическая');

INSERT INTO category
(id, name)
VALUES
(1, 'Недвижимость'),
(2, 'Транспорт'),
(3, 'Детские товары');

INSERT INTO sub_category
(id, name, category_id)
VALUES
(1, 'Квартира', 1),
(2, 'Гараж', 1),
(3, 'Дом', 1),
(4, 'Апартаменты', 1),
(5, 'Машина', 2);
