create table Prices (
                        price_id integer not null,
                        brand_id integer not null ,
                        start_date timestamp,
                        end_date timestamp,
                        price_list integer,
                        product_id integer,
                        priority integer ,
                        price decimal(20,4),
                        curr varchar(255),
                        primary key (price_id)
);