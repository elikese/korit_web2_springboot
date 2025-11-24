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
where
	product_id = ( # = 연산자는 매칭되는 값이 하나일때
		select
			product_id
		from
			orders
		order by order_date desc
        limit 1
    );


# 주문내역이 존재하는 상품만 조회
select
	*
from
	product
where
	product_id in ( # in 연산자는 매칭되는 값이 여러개일때
		select
			product_id
		from
			orders
    );

# 주문내역이 존재하지 않는 상품들만 조회
select
	*
from
	product
where
	product_id not in ( # not in을 사용해서 주문된 상품 id에 없는 상품만 필터링
		# 주문된 상품 id 목록 반환
		select
			product_id
		from
			orders
    );

# exists 연산자
# 조건을 만족하는 첫번째 행을 발견하면 즉시 true를 반환하고 종료 -> 경우에 따라 빠름
# 주문된 내역이 있는 상품만 조회
select
	*
from
	product p
where
	# 인스턴스의 id(product_id)가 주문테이블에 존재하는지 확인
    exists ( # 행(row)의 존재여부만 판단
		select
			1 # 목적이 존재여부이기 때문에 반환값은 의미가 없다.
		from
			orders o
		where
			p.product_id = o.product_id
    );


# 실습) where 서브쿼리를 작성해서 2024년 1월에 주문된 상품들만 조회
select
	*
from
	product
where
	product_id in (
		# orders 테이블에서 2024년 1월에 주문된 행의 product_id만 조회
		select
			product_id
		from
			orders
		where
			# 1월에 주문 -> 2024-01-01 이상, 2024-02-01미만
            # between을 쓰면 날짜계산에서 말일계산이 까다롭다.
			order_date >= '2024-01-01' and order_date < '2024-02-01'
            # between: 2024-01-01 00:00:00 ~ 2024-01-31 23:59:59 값을 보겠다
            # 23:59:59.9999999초 -> 누락될 가능성 존재
            # 날짜계산시 ~이상 , ~미만을 사용이 권장된다!
    );

# exists 버전
select
	*
from
	product p
where
	exists ( # 연산중인product_id가 orders테이블의 product_id에 있는지 검색
		select
			1 # 존재여부만 필요하기 때문에 아무값이나 가능
		from
			orders o
		where
			o.product_id = p.product_id
            and o.order_date >= '2024-01-01'
            and o.order_date < '2024-02-01'
    );

# from절에 사용하는 서브쿼리 - 인라인뷰
# 서브쿼리의 결과를 하나의 테이블로 간주하겠다.(가상 테이블)
select
	*
from
	(select
		product_id,
		product_name,
		product_price,
		case
			when product_price <= 30000 then '저가'
			when product_price <= 100000 then '중가'
			else '고가'
		end as `price_range`
	from
		product) as view_table # 쿼리결과를 하나의 가상테이블로도 만들수 있구나! -> view(캐싱)
where
	price_range = '중가'; # view_table : 인라인뷰(서브쿼리결과)를 하나의 테이블로 간주하고 where로 필터링













