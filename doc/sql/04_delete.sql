# delete의 기본 문법
# 가장 무서운 문법!!!!!
delete from
	product
where
	product_id = 2;

delete from
	product
where
	product_name in ("스피커", "프린터");
# where절에 in, like, 비교연산을 작성하게 된다.
# 주의!!!) 절대로 where를 빼고 delete를 하지 말자!
# delete from product -> product 테이블의 데이터 전체 삭제
/*
truncate vs delete
- delete
1. where절 가능
2. 한 row씩 삭제
3. 트랜잭션이 가능! -> 복구도 가능하다
4. auto_increment 값을 유지한다.

- truncate
1. where 안됨
2. 매우 빠름
3. auto_increment 초기화 됨.
=> 특정 조건 삭제라면 delete, 테이블을 초기화하려면 truncate

delete 안전가이드
1. where문을 작성하자
2. primary_key 기준으로 where문을 작성하자
3. select로 확인하자
4. 대량삭제는 반드시 transaction을 걸어주자(나중에)
5. FK가 걸린 테이블은 자식테이블 먼저 삭제 -> 부모테이블 삭제 or cascade를 걸자
*/

# 실습) id가 1인 product의 product_name에 "[HOT] "을 붙혀주세요
update
	product
set
	product_name = concat("[HOT] ", product_name)
where
	product_id = 1;
# 실습2) product_name에 "[HOT] "이 포함된 상품을 삭제해 주세요

delete from
	product
where
	product_name like "%[HOT]%";
	






    

	