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






