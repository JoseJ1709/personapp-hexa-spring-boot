db = db.getSiblingDB('persona_db');

db.profesion.insertMany([
    {
        "_id": NumberInt(1),
        "nom": "Ingeniero de Software",
        "des": "Desarrollo de aplicaciones y sistemas",
        "_class": "co.edu.javeriana.as.personapp.mongo.document.ProfesionDocument"
    },
    {
        "_id": NumberInt(2),
        "nom": "Médico General",
        "des": "Atención primaria en salud",
        "_class": "co.edu.javeriana.as.personapp.mongo.document.ProfesionDocument"
    },
    {
        "_id": NumberInt(3),
        "nom": "Arquitecto",
        "des": "Diseño y construcción de edificaciones",
        "_class": "co.edu.javeriana.as.personapp.mongo.document.ProfesionDocument"
    },
    {
        "_id": NumberInt(4),
        "nom": "Contador Público",
        "des": "Gestión contable y financiera",
        "_class": "co.edu.javeriana.as.personapp.mongo.document.ProfesionDocument"
    },
    {
        "_id": NumberInt(5),
        "nom": "Abogado",
        "des": "Asesoría legal y representación judicial",
        "_class": "co.edu.javeriana.as.personapp.mongo.document.ProfesionDocument"
    }
], { ordered: false });

db.persona.insertMany([
    {
        "_id": NumberInt(123456789),
        "nombre": "Pepe",
        "apellido": "Perez",
        "genero": "M",
        "edad": NumberInt(30),
        "_class": "co.edu.javeriana.as.personapp.mongo.document.PersonaDocument"
    },
    {
        "_id": NumberInt(987654321),
        "nombre": "Pepito",
        "apellido": "Perez",
        "genero": "M",
        "_class": "co.edu.javeriana.as.personapp.mongo.document.PersonaDocument"
    },
    {
        "_id": NumberInt(321654987),
        "nombre": "Pepa",
        "apellido": "Juarez",
        "genero": "F",
        "edad": NumberInt(30),
        "_class": "co.edu.javeriana.as.personapp.mongo.document.PersonaDocument"
    },
    {
        "_id": NumberInt(147258369),
        "nombre": "Pepita",
        "apellido": "Juarez",
        "genero": "F",
        "edad": NumberInt(10),
        "_class": "co.edu.javeriana.as.personapp.mongo.document.PersonaDocument"
    },
    {
        "_id": NumberInt(963852741),
        "nombre": "Fede",
        "apellido": "Perez",
        "genero": "M",
        "edad": NumberInt(18),
        "_class": "co.edu.javeriana.as.personapp.mongo.document.PersonaDocument"
    }
], { ordered: false });

db.telefono.insertMany([
    {
        "_id": "3001234567",
        "oper": "Claro",
        "duenio": NumberInt(123456789),
        "_class": "co.edu.javeriana.as.personapp.mongo.document.TelefonoDocument"
    },
    {
        "_id": "3109876543",
        "oper": "Movistar",
        "duenio": NumberInt(987654321),
        "_class": "co.edu.javeriana.as.personapp.mongo.document.TelefonoDocument"
    },
    {
        "_id": "3204567890",
        "oper": "Tigo",
        "duenio": NumberInt(321654987),
        "_class": "co.edu.javeriana.as.personapp.mongo.document.TelefonoDocument"
    },
    {
        "_id": "3157891234",
        "oper": "Claro",
        "duenio": NumberInt(147258369),
        "_class": "co.edu.javeriana.as.personapp.mongo.document.TelefonoDocument"
    },
    {
        "_id": "3006549876",
        "oper": "Movistar",
        "duenio": NumberInt(963852741),
        "_class": "co.edu.javeriana.as.personapp.mongo.document.TelefonoDocument"
    }
], { ordered: false });

db.estudios.insertMany([
    {
        "_id": "123456789-1",
        "cc_per": NumberInt(123456789),
        "id_prof": NumberInt(1),
        "fecha": ISODate("2015-06-15T00:00:00Z"),
        "univer": "Universidad Nacional",
        "_class": "co.edu.javeriana.as.personapp.mongo.document.EstudiosDocument"
    },
    {
        "_id": "987654321-2",
        "cc_per": NumberInt(987654321),
        "id_prof": NumberInt(2),
        "fecha": ISODate("2018-12-10T00:00:00Z"),
        "univer": "Universidad Javeriana",
        "_class": "co.edu.javeriana.as.personapp.mongo.document.EstudiosDocument"
    },
    {
        "_id": "321654987-3",
        "cc_per": NumberInt(321654987),
        "id_prof": NumberInt(3),
        "fecha": ISODate("2012-11-20T00:00:00Z"),
        "univer": "Universidad de los Andes",
        "_class": "co.edu.javeriana.as.personapp.mongo.document.EstudiosDocument"
    },
    {
        "_id": "147258369-4",
        "cc_per": NumberInt(147258369),
        "id_prof": NumberInt(4),
        "fecha": ISODate("2019-05-25T00:00:00Z"),
        "univer": "Universidad del Rosario",
        "_class": "co.edu.javeriana.as.personapp.mongo.document.EstudiosDocument"
    },
    {
        "_id": "963852741-5",
        "cc_per": NumberInt(963852741),
        "id_prof": NumberInt(5),
        "fecha": ISODate("2016-08-30T00:00:00Z"),
        "univer": "Universidad Externado",
        "_class": "co.edu.javeriana.as.personapp.mongo.document.EstudiosDocument"
    }
], { ordered: false });