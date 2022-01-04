DELETE FROM users WHERE TRUE;

INSERT INTO users (user_id, password, user_role, name_first_name, name_last_name,
                   address_street_name, address_street_number, address_postal_code,
                   address_city_name, email_user_name, email_domain_name, email_extension,
                   phone_number_digits, phone_number_country)
VALUES (
           '8216c35b-68f2-4038-b683-df2e2dbab9df',
           'adminpassword',
           'ADMIN',
           'adminFirstName',
           'adminLastName',
           'adminStreet',
           0,
           0,
           'adminCity',
           'admin',
           'mail',
           'com',
           '0000000000',
           'Belgium')