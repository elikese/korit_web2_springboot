/*
view : 가상 테이블
1. join을 많이 하게되면, select문이 상당히 길어진다.
2. 매번 똑같은 join을 해야하는 경우가 생겨난다.
-> join 결과를 미리 이름을 지어서 저장할 가상테이블이 필요하다!
진짜 테이블이 아니기 때문에, 실제 데이터를 저장하지 x
*/

# orders + customers + order_details + product 의 inner join결과를 view로 저장

create view order_full_view as
select
	o.order_id, o.order_date,
    c.customer_id, c.customer_name,
    c.customer_phone, c.customer_address,
	od.order_detail_id, od.product_id, od.quantity,
    p.product_name, p.product_price
from
	orders o
		inner join customers c on o.customer_id = c.customer_id
        inner join order_details od on o.order_id = od.order_id
        inner join product p on od.product_id = p.product_id;

# view를 테이블처럼 사용 가능하다.
# 쿼리를 저장한것이다 -> order full view를 select하면, 저장한 쿼리가 재동작하는 것
# 성능상 이슈가 존재 할 수 있다.
# 권장) view에 join을 하지맙시다. 하더라도 테이블 하나만 합시다


select 
	*
from
	order_full_view;


# view + group by -- 자주 쓴다
select
	product_id,
    product_name,
    sum(quantity) as `total_sold_count`
from
	order_full_view
group by
	product_id, 
    product_name;









