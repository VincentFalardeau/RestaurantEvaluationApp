create table Restaurants(
idRestaurant number(5),
nomRestaurant varchar(100),
adresseRestaurant varchar(100),
qualiteBouffe varchar(20),
qualiteService varchar(20),
prixMoyen number(6,2),
nbEtoiles number(1),
CONSTRAINT pk_resto PRIMARY KEY(idRestaurant));

commit;

select * from restaurants;