# DML - 2. select (조회)

# 테이블 전체 조회
select
	* # 전부(모든 컬럼)
from
	product; # 테이블명

# 조건을 걸 수 있다 - WHERE
select 
	*
from
	product
where
	product_price > 50000; # 자바의 stream - filter랑 동일

# 연산자 우선순위
/*
1. () 
2. not
3. *, /, %
4. +, -
5. 비교연산 =, >, <, >=, <=, between, in, like, is null
6. and > or
*/

# in
select
	product_name, product_price # 보고싶은 컬럼을 지정할 수 있다.
from
	product
where
	product_name in ("노트북", "키보드"); # 둘 중 하나면 선택
    # product_name = "노트북" or product_name = "키보드"
# from -> where -> select

insert into
	product
values
	(null, "노트북", 1500000);

# select에서 distinct를 걸면, 중복을 제거한 결과를 볼 수 있다.
# 상품명 중복 제거
select distinct
	product_name
from
	product;

# Limit & order by
select
	* # *은 실무할때 쓰면 x, count(*) x
from
	product
order by
	product_price # 가격 오름차순으로 정렬
limit
	3; # 상위 3개만 출력

select
	* # *은 실무할때 쓰면 x, count(*) x
from
	product
order by
	product_price desc # 가격 내림차순 정렬(desc)
limit
	3; # 상위 3개만 출력

# 가격 비싼순서로 4,5,6등 뽑아보기 - offset
select
	*
from
	product
order by
	product_price desc
limit 3 offset 3; # 3개를 건너뛰고 3개를 조회
# 한 게시판에서 게시글 20개를 보여줄때
# 3페이지를 조회 시,
# limit 20 offset 20 * (page - 1)

# 가격이 null인 상품을 조회 - is null / is not null
select
	*
from 
	product
where
	product_price is not null;

# 숫자 범위 검색 - between
# 상품가격 1만원~5만원
select
	*
from
	product
where
	product_price between 10000 and 50000; # 10000 ~ 50000 범위

# 문자열 패턴검색 - like
select
	*
from
	product
where
	product_name like "마%"; # "마"로 시작하는 이름
# 마% : "마"로 시작하는 이름 -> fast(unique혹은 pk가 걸려있어야 한다)
# %마 : "마"로 끝나는 이름
# %마% : "마"가 포함된 이름
# 박%목% : "박"으로 시작하고, "목"을 포함하는 이름
# 김__: 김으로 시작하고, 3글자 -> fast(unique혹은 pk가 걸려있어야 한다)
# 김_: 김으로 시작하고, 2글자
# _화_: 중간이 "화"인 3글자

# 실습1) "북"으로 끝나는 상품을 조회해 주세요
select
	*
from
	product
where
	product_name like "%북";

# 실습2) 상품가격이 30000 ~ 100000 사이 상품을 조회해 주세요
select
	*
from
	product
where
	product_price between 30000 and 100000;

# 실습3) 50000 이상 상품중들을 가격기준으로 내림차순 정렬
select
	*
from
	product
where
	product_price >= 50000
order by
	product_price desc;


# 실습4) 가격이 높은 순으로 5~8등의 상품이름,상품가격 조회 + 중복은 제거
# 
select distinct
	product_name, product_price
from
	product
order by
	product_price desc
limit 4 offset 4;
# order by에 들어간 컬럼은 select에 있는것이 표준 -> mySql은 허용
# 다만, distinct를 걸면, 반드시 있어야 한다.
# select에서 product_name만 입력하면가격순 5~8등을 보장하지 못 함

# 별칭기능 - as
select
	product_name as `상품 이름`,
    product_price as `상품 가격`,
    product_price * 1.1 as `부가세포함` # 부가세를 포함한 가격
from
	product;

# 집계함수
select
	count(*) # product에 등록된 상품의 갯수(row의 수)
    # count(*) -> null도 카운팅한다
    # count(컬럼) -> 해당 컬럼의 값이 null이면 카운팅 안한다.
from
	product;

select
	avg(product_price) as `상품가격 평균` # 평균
    # max(product_price) -> 상품가격 중 최대 가격
    # min(product_price) -> 상품가격 중 최소 가격 
from
	product;



# 그룹화 & case문법 - group by
# 저가, 중가, 고가를 임의로 나누고, 각 카테고리에 상품들이 몇개씩 있는가?

select
	# case문(조건문)
    case # if문 시작
		when product_price <= 50000 then '저가'
        when product_price <= 100000 then '중가'
        else '고가'
	end as `price_range`, # if문 종료
    count(*) as `counting`
from
	product
group by
	price_range # price_range의 결과가 같은것들을 하나로 묶겠다.
having
	count(*) >= 3; # 각 그룹의 count결과가 3이상인 것들만 -> 그룹조건

/*
select 전체 실행 순서
: row(가로줄)를 먼저 제거하는게 우선순위가 높다.
select, where, from, group by, having, order by
1. from : 어떤 테이블에서 데이터를 가져올지 결정
2. where : 가져온 데이터 중 조건을 만족하는 row만 남김
3. group by : 행을 그룹으로 묶음
4. having : 그룹 조건 -> 조건을 만족하는 그룹만 남김
5. select : 어떤 컬럼(세로줄)을 출력할건지 결정
6. order by : 출력 순서 조정
7. limit : 몇 개까지 출력할지 제한
*/

# 실습) case문과 group by를 사용하여 저가, 중가, 고가별 평균가격을 구해주세요.
select
	# product_name -> 그룹화 해버린 상태에서 한칸에 여러개의 이름을 표현하세요 -> 불가능
    # group by에 있는 컬럼이거나, 집계함수는 select에 선언할 수 있다.
	case
		when product_price <= 50000 then '저가'
        when product_price <= 100000 then '중가'
        else '고가'
	end as `price_range`, # price_range컬럼에 들어갈 값은 저가,중가,고가 중 하나
	avg(product_price) as `평균가` # 평균가라는 컬럼
from
	product
group by
	price_range;


    
    
    






