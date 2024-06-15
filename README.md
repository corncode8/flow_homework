## 프로젝트 구성
- Gradle + Java 17
- SpringBoot
- Spring Data JPA - Mysql + QueryDSL
- AWS EC2 - RDS 
- CI/CD - Jenkins

## 개요
허용 IP 및 시간대를 설정 및 제한할 수 있는 어드민 페이지입니다.
해당 페이지를 통해 관리자는 특정 IP 주소와 시간대에만 접근을 허용하도록 설정할 수 있습니다.
어떠한 시간대를 사용하더라도 항상 사용자 디바이스의 시간대로 출력되어야 합니다.

### 도메인 중심 설계
- 변경에 용이하도록 비즈니스 로직을 JPA 의존성으로부터 격리하는 방식을 선택하였습니다.
- Component Layer를 활용하여 비즈니스 로직의 책임을 분리하였습니다.
- Usecase를 활용하여 Component들을 조립하여 비즈니스 로직을 완성하였습니다.

### Spring Data JPA
- Database의 안정적인 CRUD를 위해 사용하였습니다.
- DataSource Layer의 변경이 상위 계층에 영향을 주는 것을 최소화 하기 위해 DIP를 통하여 도메인 중심적인 계층 아키텍처로 구현하였습니다.

### QueryDSL
- 검색 기능의 동적 쿼리 구현을 위해 사용하였습니다.
- JPQL, Criteria API보다 간결한 문법으로 쿼리를 작성할 수 있고, 컴파일 타임에 쿼리 오류를 잡을 수 있어 런타임 오류를 줄일 수 있습니다.

## 추가 적용 요구사항
#### IP 추가
- IP 추가시 설명을 더 명확하게 전달하기 위해 숫자만 입력하는 경우를 방지하였습니다.

#### 검색
- 검색시에 사용 시작 시간만을 입력하였더라도 사용 끝 시간은 LocalDataTime.now()로 적용되어 원활하게 검색을 진행할 수 있도록 구현하였습니다.

## 고려사항
### 아키텍처
백엔드 서버에서 일반적으로 사용하는 레이어드 아키텍처를 기반으로 비즈니스 로직 내에서 구현 계층을 분리하여 비즈니스 로직을 명확하게 전달하는 데에 집중하였습니다.
또한 레이어드 아키텍처의 단점을 보환하기 위해 하향식 의존이 아닌 도메인 중심 의존을 만들어 비즈니스 로직을 보호하고 DataSource Layer와 Presentation Layer의 유연함을 보장하였습니다.
이를 통해 외부로 노출되는 Presentation 계층이 변경되거나 데이터에 대한 제어를 제공하는 주체가 변경되더라도 유연한 변경이 가능한 구조를 제공합니다.

### 의존성
Component Layer를 이용하여 서로 다른 도메인간 최소한의 개입을 고려할 수 있습니다.
다른 도메인의 컴포넌트를 조립해 비즈니스 로직을 완성하는 것은 Usecase에 한정합니다
Service와 Controller간의 강결합을 UseCase를 사용하여 책임을 한가지씩 위임하여 해결하였습니다. 
또한 Service 처럼 여러 기능이 한 곳에 모여있지 않고 한 개의 UseCase가 단 하나의 비즈니스 로직을 담당하기 때문에 관련 없는 요구사항으로 인해 변경될 여지가 사라집니다.
각 UseCase는 한가지 책임만을 갖게되고, 상태를 갖지 않기 때문에 테스트 범위가 명확해지고 간편해집니다.

## 추가 개선 고려사항
- 테이블 구조
  - Table: WhiteList
    - id bigint AI PK <br>
      createdAt datetime <br>
      updatedAt datetime <br>
      description varchar(20) <br>
      end_time datetime <br>
      ip_address varchar(45) <br>
      start_time datetime <br>
      status varchar(10) <br>
<br>
    
### No Offset 페이지네이션
현재는 데이터베이스에 100만개의 Dummy Data가 있고, 100만개의 Dummy Data가 모두 출력되고 있기 때문에 Offset 페이지네이션의 문제점이 가장 잘 들어나고 있습니다.
Offset 페이지네이션은 매번 페이지단위로 offset 값을 계산하여 DB에 쿼리를 요청하고, 이후에 원하는 데이터를 가져옵니다.
이 방식은 데이터가 많을 경우 성능상 이슈가 발생합니다.
또한 페이지를 이동하는 중간에 데이터가 삽입, 삭제, 업데이트 등의 작업이 발생한다면 다른 페이지에 영향을 미치게 되고 중복 조회가 될 가능성이 있습니다.
이것을 No Offset 페이지 네이션으로 해결할 수 있습니다.
No Offset 방식은 이전 페이지의 마지막 데이터의 id 값을 기억하여 다음 페이지를 요청할 때 기억했던 id 값 이후의 데이터를 가져오는 방식으로,
이를 통해 매번 offset 값을 계산하지 않아도 되기 때문에 데이터베이스에서 더욱 효율적으로 데이터를 가져올 수 있습니다.<br>
<br>
### DB Query Optimization
검색 기능에서의 설명으로 검색, 사용 시작 시간, 사용 끝 시간으로 검색하는 경우 밑의 쿼리로 검색이 진행되게 됩니다.

```sql
SELECT
  WhiteList.id AS id,
  WhiteList.ip_address AS ip_address,
  WhiteList.description AS description,
  WhiteList.start_time AS start_time,
  WhiteList.end_time AS end_time
FROM
  WhiteList
WHERE
  WhiteList.description LIKE '%flow%'
ORDER BY
  WhiteList.created_at DESC
LIMIT 10 OFFSET 0;
```
해당 쿼리의 경우 Full Table Scan이 발생하고 있기 때문에 검색 성능에 많은 영향을 미치고 있습니다.<br>
검색 성능을 최적화하기 위해 파티셔닝을 통해 데이터 검색시 필요한 부분만 탐색하여 성능을 증가시키고, Full Scan에서 데이터의 Access의 범위를 줄여 성능 향상을 가져올 수 있습니다.<br>
Table간 Join에 대한 비용이 증가하지만 파티셔닝을 해도 테이블의 개수는 현저히 적기 때문에 검색 성능 향상에 있어서 더 이점이 있을 것이라고 생각합니다.<br>
하지만 파티셔닝은 항상 성능 향상을 보장하는 것이 아니기 때문에 검색시 사용자가 입력한 값에 의해 다른 쿼리가 발생하도록 하여 Index를 통한 검색이 가능하도록 설계할 수 있습니다.
