drop  database j2mvc_example;
create database j2mvc_example;
grant all privileges on j2mvc_example.* to j2mvcuser@127.0.0.1 identified by 'j2mvcpass' WITH GRANT OPTION;
grant all privileges on j2mvc_example.* to j2mvcuser@localhost identified by 'j2mvcpass' WITH GRANT OPTION;
flush privileges;

use j2mvc_example;
 
-- source D:/works/gitrepos/j2mvc-example/j2mvc-example-web/docs/mysql.sql
-- 用户
DROP TABLE IF EXISTS users;
CREATE TABLE IF NOT EXISTS users(
	id					varchar(32)	NOT NULL,
	username			varchar(255)	NOT NULL COMMENT '用户名',			
	password			varchar(255)	NOT NULL COMMENT '密码',				
  	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 购物车
DROP TABLE IF EXISTS products;
CREATE TABLE IF NOT EXISTS products(
	id					varchar(32)		NOT NULL,
	title				varchar(255)	NOT NULL COMMENT '商品标题',			
	detail				varchar(255)			 COMMENT '商品详情',				
	price				double(11,2)	NOT NULL COMMENT '价格',		
	stock				int(11)			NOT NULL COMMENT '库存',
  	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert products values
('10001','商品10001','商品10001详情',58.00,100),
('10002','商品10002','商品10002详情',31.00,40);

-- 购物车
DROP TABLE IF EXISTS carts;
CREATE TABLE IF NOT EXISTS carts(
	id					varchar(32)	NOT NULL,
	user_id				varchar(255)	NOT NULL COMMENT '用户ID',			
	product_id			varchar(255)	NOT NULL COMMENT '商品ID',				
	num					int(11)	NOT NULL COMMENT '数量',				
  	PRIMARY KEY (id),
  	FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

