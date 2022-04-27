
CREATE TABLE users (
    id int auto_increment primary key,
    sex varchar(5) NULL default '男',
    password varchar(100) default '',
    nick_name varchar(50) default '新用户',
    birthday datetime DEFAULT CURRENT_TIMESTAMP,
    reg_time timestamp DEFAULT CURRENT_TIMESTAMP,
    head_picture varchar(255) default 'http://img.suimeikeji.com/touxiang.jpg',
    disable bit(1) default false,
    open_code varchar(100) NULL,
    amount decimal(10,2)   default 0,
    yongjin decimal(10,2)  default 0,
    yongjin_code varchar(100) ,
    bind_yongjin_code  varchar(100) ,
    created_time timestamp DEFAULT CURRENT_TIMESTAMP ,
	modified_time timestamp  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
create unique index open_code_user_idx on users(open_code)
CREATE TABLE roles (
    id int auto_increment primary key,
    role_name varchar(20) NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
insert into roles values(1, 'BUYER');
insert into roles values(2, 'JIANHUO');
insert into roles values(3, 'ADMIN');


create table orders(
    id int auto_increment primary key,
    order_num varchar(64) not null ,
    user_id int not null ,

    address_id int not null ,
    address_detail varchar(400),
    address_contract varchar(100),
    yongjin_code varchar(100),
    -- 钱
    status varchar(50) not null comment '给用户看的状态信息， 待支付，待发货，待收货，待评论，已完成，超时取消，手动取消',

    total_price decimal(10, 2) not null ,
    use_yongjin decimal(10, 2) not null default 0 ,
    use_yue decimal(10, 2) not null default 0 ,
    need_pay_money decimal(10, 2) not null  ,
    had_pay_money decimal(10, 2) default 0 not null ,

    delivery_fee decimal(10, 2) default 0  not null ,
    message varchar(300),
    deleted boolean default false,

    created_time timestamp DEFAULT CURRENT_TIMESTAMP ,
	modified_time timestamp  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP

)ENGINE=InnoDB DEFAULT CHARSET=utf8;
create index orders_uid_status_idx on orders(user_id, status)
create index orders_drawback_status_idx on orders(drawback_status)
create index orders_order_num_idx on orders(order_num)

create table orders_item(
    id int auto_increment primary key,
    order_id int,

    product_id int ,
    product_name varchar(200),
    product_profile_img varchar(200),
    product_size varchar(200),
    product_cnt int,
    product_total_price decimal(10, 2),
    product_unit_price  decimal(10, 2),

    product_sanzhuang boolean default false,
    chajia_total_weight varchar(100),
    chajia_total_price decimal(10, 2),

    jianhuo_success boolean default false,
    jianhuo_time timestamp,

    created_time timestamp DEFAULT CURRENT_TIMESTAMP ,
	modified_time timestamp  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP

)ENGINE=InnoDB DEFAULT CHARSET=utf8;



create table products(
    id int auto_increment primary key comment '商品ID',
    name varchar(200) not null comment '商品名称',
    size varchar(200) not null comment '商品包装',
    second_category_id int not null comment '商品关联二级分类',
    sanzhung bit(1) default false comment '是否散装',
    show_able bit(1) default true comment '上下架标志',
    code varchar(100) comment '条形码',
    stock int default 0 comment '库存',

    origin_price decimal(10,2) not null comment '原价',
    cost_price decimal(10,2) not null default 0 comment '成本价, 当时散装的时候 线上散装元/500g',
    current_price decimal(10, 2) not null comment '售价',

    supplier_id int comment '供应商',
    sort int default 0 not null comment '排序',

    profile_img varchar(200) not null comment '封面图片',
    lunbo_imgs varchar(2000) not null comment '轮播图片',
    detail_imgs varchar(2000) comment '商品详情页图片',

    sales_cnt int default 0,
    comment_cnt int default 0,

    zhuanqu_id int default 0 comment '专区ID',
    created_time timestamp DEFAULT CURRENT_TIMESTAMP ,
	modified_time timestamp  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP

)ENGINE=InnoDB DEFAULT CHARSET=utf8;
create index products_code_idx on products(code);
