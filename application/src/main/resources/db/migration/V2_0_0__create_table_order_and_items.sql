CREATE TABLE IF NOT EXISTS "orders"."orders" (
    id Serial PRIMARY KEY,
    number_order varchar not null,
    created_at timestamp NULL DEFAULT now(),
    update_at timestamp NULL
);

CREATE TABLE IF NOT EXISTS "orders"."items" (
    id Serial PRIMARY KEY,
    item_id int4 not null,
    order_id int4 not null,
    distribution_center varchar not null,
    created_at timestamp NULL DEFAULT now()
);


ALTER TABLE "orders".items ADD CONSTRAINT fk_item_order FOREIGN KEY (order_id) REFERENCES "orders".orders(id);