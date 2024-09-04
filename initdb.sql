CREATE TYPE user_role AS ENUM ('ADMIN', 'USER');

CREATE TYPE todo_status AS ENUM ('INCOMPLETE', 'IN_PROGRESS', 'COMPLETE');

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    role user_role NOT NULL DEFAULT 'USER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE todo_lists (
    id SERIAL PRIMARY KEY,
    list_name VARCHAR(100) NOT NULL,
    user_id INT REFERENCES users(id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE todos (
    id SERIAL PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    due_date DATE,
    status todo_status NOT NULL DEFAULT 'INCOMPLETE',
    list_id INT REFERENCES todo_lists(id) ON DELETE CASCADE,
    user_id INT REFERENCES users(id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO users (username, password, email, role) VALUES 
('admin', '$2a$12$p3U8H5oN6st8LzBJij1vXOx9B81gt3sISEjMha6LNhMlTgLN7Hwvu', 'admin@javatech.com', 'ADMIN'),
('user', '$2a$12$p3U8H5oN6st8LzBJij1vXOx9B81gt3sISEjMha6LNhMlTgLN7Hwvu', 'user@javatech.com', 'USER');

INSERT INTO todo_lists (list_name, user_id) VALUES 
('Personal', 1),
('Work', 1),
('Shopping', 2);

INSERT INTO todos (title, description, due_date, status, list_id, user_id) VALUES 
('Buy groceries', 'Buy milk, bread, and eggs', '2024-09-10', 'INCOMPLETE', 3, 2),
('Complete project report', 'Finish the report by end of day', '2024-09-05', 'IN_PROGRESS', 2, 1),
('Morning workout', '30-minute morning exercise', '2024-09-06', 'COMPLETE', 1, 1);

SELECT * FROM users;
SELECT * FROM todo_lists;
SELECT * FROM todos;