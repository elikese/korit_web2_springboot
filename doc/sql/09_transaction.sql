/*
트랜잭션 -> 여러 sql문을 하나의 작업으로 묶는 문법
"송금"이라는것을 수행할때
1. 나의 계좌에서 돈이 빠져나가야한다 -> 하나의 sql문 (update)
2. 상대방 계좌에 돈이 들어와야한다 -> 하나의 sql문 (update)
3. 내역이 저장되어야 한다. -> 하나의 sql문 (insert)
: insert, update, delete가 하나의 행위로 묶여 있을 수 있다.
가상 log에 기록을해서 복원이 가능하게 만들 수 있다.
-> 1,2,3을 수행하는 도중 하나라도 실패하면, 전체 작업을 rollback 할 수 있다.
*/

# orders 삭제하기전에 참조하는 order_details 테이블의 row먼저 삭제해야함
# -> 하나의 트랜잭션으로 묶을 수 있다.

start transaction;
# 주문상세(자식테이블) row 삭제
delete from
	order_details
where
	order_id = 1;

# 주문(부모테이블) row 삭제
delete from
	orders
where
	order_id = 1;

# 저장
commit;
# 실패시 원복
rollback;
/*
스프링부트에서 트랜잭션 쿼리를 직접 작성해서 전송 하나요?
-> 아니오.
어노테이션으로 트랜잭션을 걸어줄 수 있다.(메서드에)
-> 메서드가 정상적으로 리턴되면, commit!
-> 메서드 실행 중 예외가 나타나면, rollback!
*/





