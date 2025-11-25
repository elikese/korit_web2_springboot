# 07_join.sql
/*
여러 테이블을 좌우로 연결해서 하나의 결과를 만드는 방법
연결하는 역할을 하는게 외래키(FK)
*/

# 주문 + 고객 이름 조회
# inner join -> 두 테이블 모두에서 매칭되는 값이 있는 row만 가져온다.
# 매칭이 안될경우 -> 해당 row를 가져오지 않는다.
select
	o.order_id,
    o.order_date,
    o.customer_id,
    c.customer_name
from
	orders o 
inner join customers c
		on o.customer_id = c.customer_id; # 가져오는 데이터(조인) 조건
# on과 where의 차이
# on은 조인조건으로 두 테이블을 합쳐서 새로운 가상테이블을 만드는 것
# where는 테이블에 조건을 걸어 필터링

# 주문 + 주문상세 + 상품이름
select
	o.order_id,
    o.order_date,
    o.customer_id,
    c.customer_name,
    od.quantity,
    p.product_name
from
	orders o
inner join customers c
	on o.customer_id = c.customer_id
inner join order_details od
	on o.order_id = od.order_id
inner join product p
	on od.product_id = p.product_id;
    
# left join - 왼쪽테이블 기준으로 모두 가져오겠다.
# 왼쪽테이블(from A left join B) : "A"
# 왼쪽테이블 데이터는 다 출력되고, B는 매칭되는 것만 출력

# 고객이 주문을 했는지 여부 
# customers left join orders -> 고객들 id가 모두 기재
# orders left join customers -> orders에는 주문한 고객id만 기재

select # customer left join orders -> 전체 고객기준으로 orders 테이블 데이터를 붙히겠다.
	c.customer_id,
	c.customer_name,
    o.order_id,
    o.order_date
from
	customers c # customers 테이블 데이터는 모두 표현되어야 한다.(left join)
left join orders o
	on c.customer_id = o.customer_id;

select
	c.customer_id,
	c.customer_name,
    o.order_id,
    o.order_date
from
	orders o # orders 기준으로 left join -> 이미 주문한 고객 id들만 가지고 있음
left join customers c
	on c.customer_id = o.customer_id;

    
# 실습) 모든 주문에 대해 (orders 테이블 시작으로) - inner join
# order_id, customer_name, product_name, quantity를 조회해 주세요.
select
	o.order_id,
    c.customer_name,
    p.product_name,
    od.quantity
from
	orders o 
inner join customers c
	on o.customer_id = c.customer_id
inner join product p
	on o.product_id = p.product_id
inner join order_details od
	on o.order_id = od.order_id;


# 고객이 주문을 했는지 여부를 파악하고 싶다.
# left join을 한 이유: inner join을 하면 주문이 있는 고객만 출력
# left join을 하면 모든 고객들 데이터는 남아 있음 -> 주문없음 상태를 출력가능(null)
select
	*
from
	customers c
left join orders o
	on c.customer_id = o.customer_id
where # order_id가 null -> 주문을 안했다.
	o.order_id is null;
    
# 고객(from customers)별 총 주문 금액(상품가격 * 갯수)을 집계해보자!
# 고객 -> 주문 -> 주문상세(갯수) -> 상품(상품가격)

select
	c.customer_id, 
    c.customer_name,
    sum(p.product_price * od.quantity) as `total_order_price`
from
	customers c
    inner join orders o
		on c.customer_id = o.customer_id
	inner join order_details od
		on o.order_id = od.order_id
	inner join product p
		on od.product_id = p.product_id
group by 
	c.customer_id, c.customer_name;

# 각 상품별(from product) 주문(order_detail) 수 출력
# product left join order_detail -> 주문이 0번인 상품도 함께 보여야 하기 때문

select
	p.product_id, 
    p.product_name,
    count(od.order_id) as `order_count` # order_detail 한 row가 한번의 주문횟수를 의미
    # count(*) -> null row도 카운팅한다.
    # count(컬럼) -> null row는 카운팅 안한다.
from
	product p
left join
	order_details od
		on p.product_id = od.product_id
group by
	p.product_id, 
    p.product_name;

# 실습) 주문이 한번도 없는 상품을 조회(join)
# from product로 시작! 주문없다 -> null표현이 가능해야함 -> left join
select
	*
from
	product p
left join
	order_details od # od의 row 1개는 주문 1건에 해당
		on p.product_id = od.product_id
        # join 결과 오른쪽 테이블이 null 이라는것 -> 주문 0건
where
	order_detail_id is null; # null 과 비교는 반드시 is null / is not null


# 가장 많이 팔린 상품(1등)을 조회하는 쿼리를 작성해 주세요
# from product, 팔린갯수정보(order_details.quantity)
# inner join -> product inner join order_details
# order_details inner join product

select
	p.product_id,
    p.product_name,
    sum(od.quantity) as `total_sold_count`
from
	product p
inner join
	order_details od
    on p.product_id = od.product_id
group by
	p.product_id, 
    p.product_name
order by
	total_sold_count desc
limit 1

# 외래키 제약을 걸 수 있다.
# 1. RESTRICT: 부모 테이블의 행이 삭제되거나 수정될때, 
# 자식 테이블에 외래키가 존재하면 작업을 허용하지 않겠다 -> 권장
# ex) order_id가 1인 row가 orders 테이블에서 삭제될 때,
# order_details에 order_id가 1인 row가 있다면, 삭제를 제한한다.
# -> order_details에서 먼저 삭제하고, orders에 있는 row를 삭제해야한다.

# 2. CASCADE: 부모가 삭제, 업데이트 되면, 자식 외래키값도 자동으로 삭제, 업데이트
# ex) order_id가 1인 row가 orders 테이블에서 삭제되면,
# order_details의 order_id가 1인 row들도 삭제하라 -> 최후의 수단
# 서버에 주석으로 CASCADE 내용을 문서화하는게 중요하다.

# 3. SET NULL: 부모가 삭제, 업데이트 되면, 자식 외래키값을 NULL로 바꾼다.
# ex) order_id가 1인 row가 orders 테이블에서 삭제되면,
# order_details의 order_id가 1인 것들이 NULL로 값이 변경된다. (자식테이블에 NOT NULL 아닌경우)








