db = db.getSiblingDB('admin');

// Crear el usuario persona_db con permisos sobre la base persona_db
db.createUser({
    user: 'persona_db',
    pwd: 'persona_db',
    roles: [
        {
            role: 'readWrite',
            db: 'persona_db'
        },
        {
            role: 'dbAdmin',
            db: 'persona_db'
        }
    ]
});

print('Usuario persona_db creado exitosamente');

// Cambiar a la base de datos persona_db
db = db.getSiblingDB('persona_db');

print('Base de datos persona_db lista para usar');