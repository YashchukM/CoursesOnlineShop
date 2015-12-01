# Users(password - "asdasd")
INSERT INTO shop_schema.user (id, fir_name, sec_name, email, password, admin, blacklist)
VALUES (1, 'Vova', 'Pupkin', 'vova@example.com', '$2a$10$ANL9noNAlIW8zWjLnqf8seyAM5nO75JQDN4c.m4YhujXIjR8Uis8K', 0, 1);

INSERT INTO shop_schema.user (id, fir_name, sec_name, email, password, admin, blacklist)
VALUES (2, 'Ivo', 'Bobul', 'ivo@example.com', '$2a$10$vUd8G2uvd9qYdls8fOX4MuNYdAMmXOxqtKGi95zlLzUKVf/BwHglK', 0, 1);

INSERT INTO shop_schema.user (id, fir_name, sec_name, email, password, admin, blacklist)
VALUES (3, 'Вова', 'Бублик', 'vov@example.com', '$2a$10$3W8.LFulS5JJE.h4vLhuTu09cGnF8iZZsjoVykbIgTWg85SKOPQq6', 1, 0);

# Products
INSERT INTO shop_schema.product (id, name, description, available, price, image_url)
VALUES (1, 'Black armchair', 'Big and comfortable armchair from Italy', 1, 1234,
        'http://img06.deviantart.net/be37/i/2010/302/5/7/armchair_by_fatimah_al_khaldi-d31q9dp.png');

INSERT INTO shop_schema.product (id, name, description, available, price, image_url)
VALUES (2, 'Red armchair', 'Big and comfortable armchair fromo Spain', 1, 1000,
        'http://orig07.deviantart.net/8e65/f/2010/297/9/6/red_chair_by_megaiooo-d31f04u.png');

INSERT INTO shop_schema.product (id, name, description, available, price, image_url)
VALUES (3, 'White armchair', 'Exclusive armchair from the polar bear leather', 1, 5600,
        'http://images.vectorhq.com/images/previews/67f/white-chair-psd-438268.png');

INSERT INTO shop_schema.product (id, name, description, available, price, image_url)
VALUES (4, 'Sofa XD13', 'Exclusive blue sofa in barocco style.', 0, 12345,
        'http://img07.deviantart.net/1a1d/i/2012/312/0/b/antique_sofa_png_by_camelfobia-d5keis0.png');

