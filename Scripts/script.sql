create table Restaurants(
idRestaurant number(5),
nomRestaurant varchar(100),
adresseRestaurant varchar(100),
qualiteBouffe varchar(20),
qualiteService varchar(20),
prixMoyen number(6,2),
nbEtoiles number(1),
CONSTRAINT pk_resto PRIMARY KEY(idRestaurant));

create sequence seqresto increment by 1 start with 29;

create or replace 
trigger airesto 
before insert on restaurants
for each row
begin
    :new.idRestaurant := seqresto.nextval();
end;

create table QualityMap(
idquality NUMBER(1),
nomquality varchar(20),
constraint pk_qualitymap primary key (idquality));

create view viewrestaurants as select * from restaurants;

grant select on viewrestaurants to public;

create view myrestaurants as
select idRestaurant, nomRestaurant, adresseRestaurant, 
(select idQuality from qualitymap where nomQuality = r.qualiteBouffe) bouffe, 
(select idQuality from qualitymap where nomQuality = r.qualiteService) service,
prixMoyen, nbEtoiles ,
(select count(*) from restaurants rs where rs.nomRestaurant = r.nomRestaurant) nbvotes,
(select count(*) from restaurants rs where rs.nomRestaurant = r.nomRestaurant and rs.nbetoiles = 1) onestar,
(select count(*) from restaurants rs where rs.nomRestaurant = r.nomRestaurant and rs.nbetoiles = 2) twostar,
(select count(*) from restaurants rs where rs.nomRestaurant = r.nomRestaurant and rs.nbetoiles = 3) treestar,
(select count(*) from restaurants rs where rs.nomRestaurant = r.nomRestaurant and rs.nbetoiles = 4) fourstar,
(select count(*) from restaurants rs where rs.nomRestaurant = r.nomRestaurant and rs.nbetoiles = 5) fivestar
from restaurants r;

create view myrestaurants2 as
select nomRestaurant, adresseRestaurant, 
avg(bouffe) as avgbouffe, avg(service) as avgservice, 
avg(prixMoyen) as avgprice, avg(nbEtoiles) as avgetoiles,
sum(nbvotes) as nbvotes,
sum(onestar) as onestar, sum(twostar) as twostar, 
sum(treestar) as treestar, sum(fourstar) as fourstar, sum(fivestar) as fivestar from
(select nomRestaurant, adresseRestaurant, 
(select idQuality from qualitymap where nomQuality = lower(r.qualiteBouffe)) bouffe, 
(select idQuality from qualitymap where nomQuality = lower(r.qualiteService)) service,
prixMoyen, nbEtoiles,
(select count(*) from restaurants rs where rs.nomRestaurant = r.nomRestaurant) nbvotes,
(select count(*) from restaurants rs where rs.nomRestaurant = r.nomRestaurant and rs.nbetoiles = 1) onestar,
(select count(*) from restaurants rs where rs.nomRestaurant = r.nomRestaurant and rs.nbetoiles = 2) twostar,
(select count(*) from restaurants rs where rs.nomRestaurant = r.nomRestaurant and rs.nbetoiles = 3) treestar,
(select count(*) from restaurants rs where rs.nomRestaurant = r.nomRestaurant and rs.nbetoiles = 4) fourstar,
(select count(*) from restaurants rs where rs.nomRestaurant = r.nomRestaurant and rs.nbetoiles = 5) fivestar
from viewrestaurants r union all
select nomRestaurant, adresseRestaurant, 
(select idQuality from qualitymap where nomQuality = lower(r2.qualiteBouffe)) bouffe, 
(select idQuality from qualitymap where nomQuality = lower(r2.qualiteService)) service,
prixMoyen, nbEtoiles,
(select count(*) from GauthierB.orarestaurant rs where rs.nomRestaurant = r2.nomRestaurant) nbvotes,
(select count(*) from GauthierB.orarestaurant rs where rs.nomRestaurant = r2.nomRestaurant and rs.nbetoiles = 1) onestar,
(select count(*) from GauthierB.orarestaurant rs where rs.nomRestaurant = r2.nomRestaurant and rs.nbetoiles = 2) twostar,
(select count(*) from GauthierB.orarestaurant rs where rs.nomRestaurant = r2.nomRestaurant and rs.nbetoiles = 3) treestar,
(select count(*) from GauthierB.orarestaurant rs where rs.nomRestaurant = r2.nomRestaurant and rs.nbetoiles = 4) fourstar,
(select count(*) from GauthierB.orarestaurant rs where rs.nomRestaurant = r2.nomRestaurant and rs.nbetoiles = 5) fivestar
from GauthierB.orarestaurant r2) group by nomRestaurant, adresseRestaurant;

select * from myrestaurants;

