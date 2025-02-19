INSERT INTO role (role) VALUES
('ADMIN'), ('USER'), ('GUEST'), ('MARKETER');

INSERT INTO client (id, name, surname, password, email, role, birthday, is_blocked) VALUES
    ('11111111-1111-1111-1111-111111111111', 'Иван', 'Иванов', 'password123', 'ivan@example.com', 'USER', '1990-05-15', FALSE),
    ('22222222-2222-2222-2222-222222222222', 'Мария', 'Петрова', 'securepass', 'maria@example.com', 'ADMIN', '1985-08-22', FALSE),
    ('33333333-3333-3333-3333-333333333333', 'Алексей', 'Сидоров', 'qwerty', 'alex@example.com', 'MARKETER', '1995-11-30', TRUE);

INSERT INTO car (id, user_id, make, model, year, transmission, fuel_type, class) VALUES
    ('aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '11111111-1111-1111-1111-111111111111', 'Toyota', 'Camry', 2020, 'Automatic', 'Gasoline', 'Sedan'),
    ('aaaaaaa2-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '22222222-2222-2222-2222-222222222222', 'BMW', 'X5', 2019, 'Automatic', 'Diesel', 'SUV');

INSERT INTO address (id, city, street, house) VALUES
    ('44444444-4444-4444-4444-444444444444', 'Москва', 'Ленина', '10A'),
    ('55555555-5555-5555-5555-555555555555', 'Санкт-Петербург', 'Невский', '25B');

INSERT INTO place (id, name, address_id, type, contact_info) VALUES
    ('66666666-6666-6666-6666-666666666666', 'АЗС Газпром', '44444444-4444-4444-4444-444444444444', 'GAS_STATION', 'Тел: +7 495 123-45-67'),
    ('77777777-7777-7777-7777-777777777777', 'Автомойка "Чистый путь"', '55555555-5555-5555-5555-555555555555', 'CAR_WASH', 'Тел: +7 812 987-65-43');

INSERT INTO service (id, place_id, service_type, description) VALUES
    ('88888888-8888-8888-8888-888888888888', '66666666-6666-6666-6666-666666666666', 'OIL_CHANGE', 'Замена масла и фильтров'),
    ('99999999-9999-9999-9999-999999999999', '77777777-7777-7777-7777-777777777777', 'WASH', 'Комплексная мойка автомобиля');

INSERT INTO user_favorite_service (user_id, service_id) VALUES
    ('11111111-1111-1111-1111-111111111111', '88888888-8888-8888-8888-888888888888'),
    ('22222222-2222-2222-2222-222222222222', '99999999-9999-9999-9999-999999999999');

INSERT INTO service_notification (id, user_id, service_type, notification_date, comment) VALUES
    ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '11111111-1111-1111-1111-111111111111', 'OIL_CHANGE', '2025-03-10 10:00:00', 'Время заменить масло'),
    ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '22222222-2222-2222-2222-222222222222', 'TIRE_CHANGE', '2025-04-01 09:30:00', 'Замена зимних шин');

INSERT INTO review (id, user_id, service_id, rating, comment, created_at, status) VALUES
    ('cccccccc-cccc-cccc-cccc-cccccccccccc', '11111111-1111-1111-1111-111111111111', '88888888-8888-8888-8888-888888888888', 5, 'Отличная замена масла!', CURRENT_TIMESTAMP, 'PUBLISHED'),
    ('dddddddd-dddd-dddd-dddd-dddddddddddd', '22222222-2222-2222-2222-222222222222', '99999999-9999-9999-9999-999999999999', 4, 'Мойка хорошая, но дороговато', CURRENT_TIMESTAMP, 'PENDING');

INSERT INTO recommendation (id, user_id, car_id, text, created_at, status) VALUES
    ('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', '11111111-1111-1111-1111-111111111111', 'aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'Рекомендуется проверять масло каждые 5000 км', CURRENT_TIMESTAMP, 'PUBLISHED'),
    ('ffffffff-ffff-ffff-ffff-ffffffffffff', '22222222-2222-2222-2222-222222222222', 'aaaaaaa2-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'Лучше использовать оригинальные запчасти', CURRENT_TIMESTAMP, 'ARCHIVED');

INSERT INTO promotion (id, title, description, created_by) VALUES
    ('gggggggg-gggg-gggg-gggg-gggggggggggg', 'Скидка 10% на замену масла', 'Акция на сервисе Газпром', '33333333-3333-3333-3333-333333333333'),
    ('hhhhhhhh-hhhh-hhhh-hhhh-hhhhhhhhhhhh', 'Бесплатная мойка', 'Бесплатная мойка при покупке топлива', '33333333-3333-3333-3333-333333333333');

INSERT INTO promotion_user (promotion_id, user_id, end_date, qr_code) VALUES
    ('gggggggg-gggg-gggg-gggg-gggggggggggg', '11111111-1111-1111-1111-111111111111', '2025-06-01 23:59:59', 'QR_DISCOUNT_10'),
    ('hhhhhhhh-hhhh-hhhh-hhhh-hhhhhhhhhhhh', '22222222-2222-2222-2222-222222222222', '2025-07-01 23:59:59', 'QR_FREE_WASH');

INSERT INTO inaccuracy (id, reporter_id, description) VALUES
    ('iiiiiiii-iiii-iiii-iiii-iiiiiiiiiiii', '11111111-1111-1111-1111-111111111111', 'Неправильное время работы мойки'),
    ('jjjjjjjj-jjjj-jjjj-jjjj-jjjjjjjjjjjj', '22222222-2222-2222-2222-222222222222', 'Некорректный адрес заправки');
