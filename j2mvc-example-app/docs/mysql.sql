create database j2mvc_example;


create database j2mvc_example;

grant all privileges on j2mvc_example.* to exmaple@127.0.0.1 identified by 'exmaplepassword' WITH GRANT OPTION;
flush privileges;

 use j2mvc_example;
 
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
	title				varchar(255)		NOT NULL COMMENT '商品标题',			
	detail				varchar(255)	NOT NULL COMMENT '商品详情',				
	price				double(11,2)	NOT NULL COMMENT '价格',		
	stock				int(11)			NOT NULL COMMENT '库存',
  	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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

