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
# 마% : "마"로 시작하는 이름
# %마 : "마"로 끝나는 이름
# %마% : "마"가 포함된 이름

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
# order by에 들어간 컬럼은 select에 있는것이 표준
# select에서 product_name만 입력하면가격순 5~8등을 보장하지 못함


# 별칭기능 - as
select
	product_name as `상품 이름`,
    product_price as `상품 가격`,
    product_price * 1.1 as `부가세포함` # 부가세를 포함한 가격
from
	product;








