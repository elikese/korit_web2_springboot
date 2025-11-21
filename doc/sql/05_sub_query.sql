/*
서브쿼리란? sql문(select로 시작해서 ;끝나는 문장) 안에 포함된 또 다른 sql문
1. select안에 사용 -> 스칼라 서브쿼리
2. where절에 사용 -> where절 서브쿼리
3. from절에 사용 -> 인라인 뷰
*/
create table orders (
	order_id int primary key,
    customer_id int not null, # -> 외래키(다른 테이블의 pk)
    product_id int not null, # -> 외래키(다른 테이블의 pk)
    order_date datetime
);

insert into
	orders
values
	(1, 1, 1, '2024-01-10'),
    (2, 2, 3, '2024-01-12'),
    (3, 1, 1, '2024-01-15'),
    (4, 3, 8, '2024-02-10'),
    (5, 2, 4, '2024-02-11');

# select안에 사용하는 서브쿼리
# 각 상품의 주문 수를 같이 출력
select
	p.product_name,
    p.product_price,
    (	# 이 상품이 orders 테이블에서 몇개 주문되었는지 계산
		select
			count(*)
        from
			orders o
		where
			o.product_id = p.product_id 
            # product_id 컬럼은 orders랑 product 둘다 존재함
            # 그래서 product 테이블은 p로, orders 테이블은 o로 식별해준 것
    ) as `order_count`
from
	product p;

# 평균가격을 스칼라 서브쿼리로 조회 - 전체 평균을 각 상품 옆에 출력

select
	product_name,
    product_price,
    (
		select
			avg(product_price)
		from
			product
    ) as `avg_price` # ()안에 작성된 sql문을 하나의 컬럼으로 보겠다.
from
	product;

# where절에 사용되는 서브쿼리
# 가장 최근에 주문된 상품을 조회
select
	*
from
	product
where # =서브쿼리 -> 결과가 반드시 하나여야 한다.(limit 1)
	product_id = ( 
		# orders 테이블에서 가장 최근주문된 product_id
		select
			product_id
		from
			orders
		order by
			order_date desc
		limit 1
    );

# 주문이 존재하는 상품만 조회
select
	*
from
	product
where
	product_id in (
		select
			product_id
		from
			orders
    );







