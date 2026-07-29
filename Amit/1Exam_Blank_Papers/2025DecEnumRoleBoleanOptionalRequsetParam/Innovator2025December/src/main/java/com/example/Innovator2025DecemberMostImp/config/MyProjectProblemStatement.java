package com.example.Innovator2025DecemberMostImp.config;

public class MyProjectProblemStatement {
	
	
	
	
/*
===========================================================================================


============================================================
EcoVote API – COMPLETE STRUCTURED DOCUMENT (FULL CONTENT)
============================================================







--====================================================================
 PROJECT STRUCTURE
=============================================================================

com.challenge.ecoVoteApplication
|
|-- configurations
|-- controllers
|-- dataloaders
|-- dto
|-- enums
|-- filter
|-- models
|-- repositories
|-- services
|-- EcoVoteApplication



Project folder structyre


Ca WingsT4_EcoVoteAPI_SpringbootM [EcoVoteApplication] ~/Des


com.challenge.ecoVoteApplication
com.challenge.ecoVoteApplication

configurations :
CustomUserDetails
CustomUserDetailsService
SecurityConfig



controllers :
ActivityController
LoginController
VoteController


dataloaders :
DataLoader


dto :
AuthRequest
JwtResponse
UpdateActivityDto


enums:
Category
Status
UserRoles


filter : 
JwtAuthFilter


models : 
SustainableActivity
UserInfo
Vote


repositories :
SustainableActivityRepository
UserInfoRepository
VoteRepository


services :
ActivityService
JwtService
LoginService
VoteService


EcoVoteApplication



test java :
EcoVoteApplication Tests



resources :

install.sh

mvnw
≤ mvnw.cmd

m pom.xml
ProblemStatement.pdf

--===========================================================================================




=================REQUIRED IMPLEMENTATIONS================================
=====================================================

- CustomUserDetails
- CustomUserDetailsService
- SecurityConfig
- JwtService
- JwtAuthFilter
- Controllers
- Services

--====================================================================


=====================================================================
 IMPORTANT RULES
=================================================================

- API paths are case-sensitive:
  /Login
  /activity/List
  /vote/List/{id}

- JSON fields must match exactly:
  postedById
  activityId
  voterId

- JWT must follow exact structure
- Do NOT modify entities


To pass:
- Follow exact API naming
- Return correct JSON structure
- Implement JWT properly
- Enforce role-based access

===============================================================================











--==========================================================================================================================
==============================================
1. PROBLEM STATEMENT , INTRODUCTION
===============================================
Eco Vote API

Build a REST API using Spring Boot to manage:
- Sustainable activities
- Votes on activities

EcoVote API is a Spring Boot REST application used to:
- Manage sustainable community activities
- Allow citizens to vote on activities
- Enforce role-based access using JWT authentication


In this hands-on, you need to create a REST Api in Spring hoot, which is used to manage details
of sustainable activities organized in a community and the votes cast upon them.

Instructions:
. The dependencies will be installed automatically. If it isn't, kindly install the required
dependencies by running 'mvn clean install' from the terminal in the project folder,
where pom.xml is present. (/Desktop/Project/WingsT4_EcoVoteAPI_SpringbootM)

. For running the application use 'myn spring-boot:run' in terminal from the project
folder.

. If you are getting port already in use error, open terminal and execute 'fuser -k 8081/tep'
to kill the application and try rerunning. Don't change the port of the application.

. For testing the application use 'mvn clean test' from the project folder
(/Desktop/Project/WingsT4_EcoVoteAPI_SpringbootM).

Note:
. Use Classes that are provided to you.
. All inputs and outputs are case sensitive.
· All authentication and authorization processes need to be implemented using JWT Token.
. JWT token should be sent as a Bearer token in Authorization request header. For example: Authorization value would be "Bearer <SPACE> <JWT TOKEN>".
. Don't modify the entities/models, as they are already complete.
. Don't try to modify or delete any other files, folders except the files mentioned above, It will cause your application to go down.
=============================================================================================






===================================================
2. SETUP INSTRUCTIONS
=====================================================
Run:
mvn clean install
mvn spring-boot:run

If port busy:
fuser -k 8081/tcp

Run tests:
mvn clean test

IMPORTANT:
- Port must be 8081
- Do NOT modify entities
- Inputs/outputs are case-sensitive


Run in project directory:

mvn clean install
mvn spring-boot:run

If port 8081 is busy:
fuser -k 8081/tcp

Run tests:
mvn clean test

Notes:
- Application must run on port 8081
- Do NOT modify entities or structure
===================================================================================







==============================================================
3. AUTHENTICATION (JWT)
=============================================================

Algorithm: HS256

Secret Key:
5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437

(Base64 encode before use)

Payload:
{
 "sub": "username",
 "iat": timestamp,
 "exp": timestamp + 30 mins
}

Header:
Authorization: Bearer <JWT_TOKEN>

Login Response:
{
 "accessToken": "token",
 "status": 200
}



JWT token Creation:
The jwt token generation should be as follows:
Algorithm HS256

SecretKey 5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437

The secret key should base64 encoded before using it to sign

Ensure that the claims in the jwt token should be as follows:

HEADER
{
"alg": "HS256",
"typ": "JWT"
}

PAYLOAD
{
"sub": "workerOne", (username - principal)
"iat": 1576071104408, (time of creation in millisecond)
"exp": 1576072904408 (expiry time in millisecond, each token is valid for 30 mins)
}

SIGNATURE : Should be signed with secretkey encoded in base64

The token upon successful login should be returned as follows:
{
"accessToken": "eyJhbGC101JIUzI1N139.eyJ20NI101JEZXYILCJOYAQ10JE3MTUANZU2NTIsINV4℃CI6MTcxNj83NTY3MnO.D7224KBZOF_v356_M4hxxuE[4YBvj2LtknPD:91FagU".
"status": 200
}
========================================================================================









================================================================
4. DATA MODELS
==================================================================


Models:
-------------------
UserInfo: 
 
Field Name   Datatype      PrimaryKey   ForeignKey    Comments



id           Integer          Yes                      autoincrement
name         String
password     String                                    base64 encoded
roles        UserRoles                                 Enum: CITIZEN, ORGANIZER 


------------------------------------
SustainableActivity:


Field Name      Datatype     PrimaryKey    ForeignKey      Comments

id              Integer       yes                          autoincrement
title           String
description     String
category        Category                                    Enum: ENERGY SAVING, GREEN SPACE, WASTE REDUCTION
postedBy        UserInfo                     yes
postDate        LocalDate                                   autocreated
status          Status                                      Enum: OPEN, CLOSED,IMPLEMENTED
estimatedCost   Integer
is Verified     boolean
location        String



------------------------------------
Vote:
Field Name   Datatype   PrimaryKey    ForeignKey     Comments


id          Integer      Yes                          autoincrement
voter       UserInfo                    yes
activity    SustainableActivity         yes
timestamp   Instant                                   autocreated
support     boolean
comment     String

----------------------------------------------------------------------------

UserInfo:
- id (Integer, PK)
- name (String)
- password (String, base64)
- roles (CITIZEN / ORGANIZER)

SustainableActivity:
- id
- title
- description
- category (ENERGY_SAVING, GREEN_SPACE, WASTE_REDUCTION)
- postedBy
- postDate
- status (OPEN, CLOSED, IMPLEMENTED)
- estimatedCost
- verified
- location

Vote:
- id
- voter
- activity
- timestamp
- support (boolean)
- comment

---------- UserInfo.java ----------
@Entity
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRoles roles;

    // getters and setters
}

---------- SustainableActivity.java ----------
@Entity
public class SustainableActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToOne
    private UserInfo postedBy;

    private LocalDate postDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Integer estimatedCost;

    private boolean verified;

    private String location;
}

---------- Vote.java ----------
@Entity
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private UserInfo voter;

    @ManyToOne
    private SustainableActivity activity;

    private Instant timestamp;

    private boolean support;

    private String comment;
}
========================ENUMS===================================


UserRoles:
- CITIZEN
- ORGANIZER

Category:
- ENERGY_SAVING
- GREEN_SPACE
- WASTE_REDUCTION

Status:
- OPEN
- CLOSED
- IMPLEMENTED
--============================================================================================================
==================================================================================================================









===========================================================================
5. PRELOADED DATA
======================================================================

Users:
1 - organizerOne (ORGANIZER)
2 - organizerTwo (ORGANIZER)
3 - citizenOne (CITIZEN)
4 - citizenTwo (CITIZEN)
5 - citizenThree (CITIZEN)

Activities:
- Community Tree Planting
- Plastic Free Challenge

Votes:
- Mixed support and opposition


Data that are loaded:

-------------------
. The following data are loaded in the UserInfo Model,

Userinfo:

Id    Name           Password                Roles
1 organizerOne   organizer123$ (encoded)   ORGANIZER

2 organizerTwo   organizer4565 (encoded)   ORGANIZER

3 citizenOne   citizen123$ (encoded)    CITIZEN

4 citizenTwo   citizen4565 (encoded)    CITIZEN

5 citizen Three   citizen789$ (encoded)   CITIZEN

------------------------
The following data are loaded in the 
SustainableActivity Model:

Id   title                      description         category postedBy   postDate     status   estimated Cost   is Verified   location
  
1   Tree Planting Community                             1       1      (current Date)    0        5000            true       CentralPark, NewYork            

Description: An event where local community members


2   Plastic Free Online Challenge                       2       2      (current Date)    0        7000           false

description : A month-long challenge  encouraging participants to reduce or eliminate single-use plastics in their daily lives.. 


-------------------------------------------
The following data are loaded in the Vote Model,

Vote:
Id   voterld    activityld   timestamp             support   comment   
          1       1           (currenttimestamp)   true      I support


2   (currenttimestamp)   false   I oppose


3   (currenttimestamp)   true   I support

2   (currenttimestamp)   false   I can't

(currenttimestamp)    false    I oppose

--==============================================================================================================
--====================================================================================










=====================================================================
6. ACCESS CONTROL
=======================================================================

Public: No authentication
Organizer: ORGANIZER role
Citizen: CITIZEN role
Owner Organizer: Organizer + creator

Failure → 403 FORBIDDEN



Note:
· Endpoints marked in GREEN color are accessed by anyone without authentication.

. Endpoints marked in BLUE color are accessed by users who are authenticated and have the role "ORGANIZER".

· Endpoints marked in ORANGE color are accessed by users who are authenticated and have role "CITIZEN".

. Endpoints marked in RED color are accessed by users who are authenticated and have role "ORGANIZER" and needs to be the creator of that object.

If any of the above validations are failing, return with a response code of 403 - Forbidden
--===================================================================================================================
--=================================================================================================









========================================================
7. ENDPOINTS
========================================================

POST /Login
GET /activity/List
GET /vote/List/{id}
POST /activity/add
PATCH /activity/update/{id}
DELETE /activity/delete/{id}
POST /vote/add/{id}

IMPORTANT:
Case-sensitive endpoints

1. LOGIN
POST /Login

Request:
{
  "username": "organizerOne",
  "password": "organizer123$"
}

Responses:
200 OK
400 Bad Request

---------------------

2. GET ACTIVITIES
GET /activity/List

Responses:
200 OK
204 No Content

---------------------

3. GET VOTES
GET /vote/List/{activityId}

Responses:
200 OK
204 No Content

---------------------

4. ADD ACTIVITY (Organizer only)
POST /activity/add

Responses:
201 Created
401 Unauthorized
403 Forbidden

---------------------

5. UPDATE ACTIVITY
PATCH /activity/update/{id}

Request:
{
  "status": "CLOSED"
}

Responses:
200 OK
400 Bad Request
403 Forbidden

---------------------

6. DELETE ACTIVITY
DELETE /activity/delete/{id}

Responses:
204 Deleted
400 Not Found
403 Forbidden

---------------------

7. ADD VOTE (Citizen only)
POST /vote/add/{activityId}

Request:
{
  "support": false,
  "comment": "I oppose"
}

Responses:
201 Created
401 Unauthorized
403 Forbidden



Endpoints:
The application should be configured to run in the port - 8081

1.POST METHOD - Jlogia
Authenticates and creates JWT token with respective authorization
Request Parameters
Success Response: 200 OK *status":200
Error Response : 400 Bad Request on invalid credentials
JSON Body -
"username""organizerOne".
1
"password":"organizer 123S"
"accessToken"""your_jwt_token".
1






--------------------------------
To get all the details of sustainable activities with status code 200. If no data is available, then
return status code 204 - NO CONTENT.

Success Response : 200 OK
Error Response : NO CONTENT 204

1.
"id": 1.
"title":" Community Tree Planting".
" description":" An event where local community members come together to plant trees in urban areas.",
" category":" GREEN_SPACE",
*postDate": "2025-06-10". (created Date)
" status": OPEN,
* estimatedCost ": 5000.
" location": " Central Park, New York".
" verified": true,
" postedByld ": 1


"id": 2.
"title":" Plastic Free Challenge ",
" description":" A month-long challenge encouraging participants to reduce or eliminate single-tase plastics in their daily lives.",
" category":" WASTE REDUCTION",
* postDate": "2025-06-10". (created Date)
"status": OPEN,
* estimatedCost ": 7000.
" location": "Online".
" verified": false,
postedByld ": 2
1.




-----------------------------------------------------------
3.GET METHOD . /vote/list/[activity_id]
To get all the votes of a sustainable activity based on activityld with status code 200. If no data is
available, then return status code 204 - NO CONTENT.

/vote/list/1
Success Response:  200 OK
Error Response : NO CONTENT 204


**: 1.

" timestamp":" 2025-06-10109:56:53.2303452. ". (created Time)
* support": tras.
" comment":" I support",
" voterid": 3,
"activityld ": 1.
1-

"id": 2.
"timestamp" :* 2025-06-11T09:00:53.2303452. ". (created Time)
* support": false,
" comment":" I oppose",
" voterld": 4,
* activityld ": 1.


------------------------------------------------
4.POST METHOD . /activity/add
Adds a new sustainable activity data. Only accessible by the ORGANIZER
Nate: podtedRyld child point to the ORGANIZER who zovatod the activity detail

Request Parameters
Success Response : 201 CREATED
Error Response : 401 UNAUTHORIZED (without jut), 403 FORBIDDEN (if citizen tries to access)
JSON Body -
JSON Body -



"title":"Electric Vehicle
1

(EV) Promotion
Na: 3.
Campaign".

"title":"Electric Vehicle (EV) 

" description":" A Promotion Campaign".

campaign to promote the

"description" A campaign to use of electric vehicles by providing information.
promote the use of electric vehicles by providing incentives, and support for
information, incentives, and
those making the switch
support for those making the
from gasoline-powered
switch from gasoline-powered
cars.".

cars. ..

"category":0,

"category""ENERGY SAVING

* status": 0,

* estimatedCost ": 10000,

" location": " Los Angeles,

"postDate": "2025-06-10".
(created Date)
CA "

"stars": OPEN,

* verified': true

"estimatedCost *: 10000,

" location": " Los Angeles, CA".

" verified": true,

"postedByld ": 1

----------------------------------------------
S.PATCH METHOD . (activity/update/[id]
Updates the activity status details.

/activity/update/1
Request Parameters

Success Response : 200 OK
Error Response: 400 BAD REQUESTS (on invalid id), 403 FORBIDDEN (if citizen tries to access)
JSON Body -


"id": 1.

"status":"1"

"title":" Community Tree Planting".

" description":" An event where local community members come together to plant trees in urban areas.".

" category":" GREEN SPACE".

"postDate": "2025-06-10". (created Date)

" status": CLOSED.

" estimatedCost ": 5000,

" location": " Central Park, New York".

" verified" : true,

"postedByld *: 1


-----------------------------------------------------------------------------------------------------
6 . DELETE METHOD - /activity/delete/[id)

Note: This Method requires authentication. User who was authenticated and have rode "ORGANIZER" and creator of the given id object, can be able to access this endpoint.

Get the activity data object with given id from sustainable activity model, delete it and return "deleted successfully" with status code 204.

If the given id is not found, then return "not found" with status code 400.

If the authenticated user is not a creator of the given id object, then return "you don't have permission" with status code 403.





------------------------------------------------------
7. POST METHOD - /voteludd/[activity_id)

Adds new vote data. Only accessible by the CITIZEN

Note: voterld should point to the CITIZEN who created the vote.

/vote/add/3
Request Parameters

Success Response : 201 CREATED
Error Response : 401 UNAUTHORIZED (without jwt), 403 FORBIDDEN (if organizer tries to access)
JSON Body -


"id": 7.

* support": false,

" timestamp":" 2025-06-11T09:00:53.230345Z ". (created Time)


oppose"

" support": false,

" comment":" I oppose".

1
" vaterid": 4.

"activityld ": 3.


--================================End Point ends here================================================
--===============================================================================================









===============================================================================================
 SECURITY CLASSES -----------------CLasses
====================================================================

---------- CustomUserDetails ----------
public class CustomUserDetails implements UserDetails {

    private String username;
    private String password;
    private List<GrantedAuthority> authorities;

    public CustomUserDetails(UserInfo user) {
        this.username = user.getName();
        this.password = user.getPassword();
        this.authorities = List.of(
            new SimpleGrantedAuthority("ROLE_" + user.getRoles().name())
        );
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public String getPassword() { return password; }
    public String getUsername() { return username; }

    public boolean isAccountNonExpired() { return true; }
    public boolean isAccountNonLocked() { return true; }
    public boolean isCredentialsNonExpired() { return true; }
    public boolean isEnabled() { return true; }
}

---------- CustomUserDetailsService ----------
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserInfoRepository repo;

    public UserDetails loadUserByUsername(String username) {
        UserInfo user = repo.findByName(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new CustomUserDetails(user);
    }
}

---------------------------------------------------------------------------
---------------------------------------------------------------------------------

package com.challenge.ecoVoteApplication. configurations;
import com.challenge.ecoVoteApplication.models.UserInfo;
import org. springframework.beans. factory.annotation.Autowired;
import org. springframework. security.core. GrantedAuthority;
import org. springframework. security. core. authority . SimpleGrantedAuthor
import org. springframework. security. core. userdetails. UserDetails;
import java. util. Arrays;
import java. util. Collection;
import java. util. List;
import java. util.stream. Collectors;


public class CustomUserDetails implements UserDetails {
public class CustomUserDetails implements UserDetails {

@Autowired
private String username;



@Autowired
private String username;

@Autowired
private String password;

@Autowired
private List<GrantedAuthority> authorities;

public CustomUserDetails(UserInfo user) {

@Override
public Collection <? extends GrantedAuthority> getAuthorities() { return null;

@Override
public Collection <? extends GrantedAuthority> getAuthorities() { return null;

@Override
public String getPassword() { return null;

@Override
public String getUsername() { return null;

@Override
public boolean isAccountNonExpired() { return false;

@Override
public boolean isAccountNonLocked() { return false;






--------------------------------------------------
package com.challenge.ecoVoteApplication.configurations;
import com. challenge.ecoVoteApplication. configurations. CustomUserDetails;
import com.challenge.ecoVoteApplication.models.UserInfo;
import com. challenge.ecoVoteApplication.repositories. UserInfoRepository;
import org.springframework.beans. factory.annotation.Autowired;
import org.springframework. security.core.userdetails. UserDetails;
import org.springframework. security. core.userdetails. UserDetailsService;
import org.springframework. security. core.userdetails. UsernameNotFoundException;
import org. springframework. stereotype. Service;
import java. util. Optional;

public class CustomUserDetailsService implements UserDetailsService {

public class CustomUserDetailsService implements UserDetailsService {

private UserInfoRepository userInfoRepository;

@Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
return null;






----------------------------------------------------------------------------------------
package com.challenge. ecoVoteApplication. configurations;
import com. challenge. ecoVoteApplication. filter . JwtAuthFilter;
import org.springframework. beans. factory.annotation. Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework. context.annotation. Configuration;
import org.springframework.data.domain. AuditorAware;
import org.springframework.data. jpa. repository.config.EnableJpaAuditing;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager:
import org.springframework. security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao. DaoAuthenticationProvider;
import org. springframework. security. config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework. security.config.annotation.method.configuration.EnableMethodSecurity;
import org. springframework. security. config. annotation.web.builders. HttpSecurity;
import org.springframework.security. config.annotation.web.configuration. EnableWebSecurity;
import org. springframework.security.config.annotation.web. configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security. core. userdetails. UserDetailsService;

--=========================================================================================













=========================================================================
--------------------------TESTING-------------------------------------------------
===========================================================================

Framework:
- SpringBootTest
- MockMvc

Tests cover:
- Login success/failure
- Fetch activities
- Fetch votes
- Role-based access

--=======================================================================================================
--==================================================================================================
==============================TEST CLASS (FULL CLEANED)==========================
========================

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class EcoVoteApplicationTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
            .webAppContextSetup(context)
            .apply(springSecurity())
            .build();
    }

    @Test
    void getAllActivities() throws Exception {
        mockMvc.perform(get("/activity/List"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void getVotes() throws Exception {
        mockMvc.perform(get("/vote/List/1"))
            .andExpect(status().isOk());
    }

    @Test
    void failedLogin() throws Exception {
        AuthRequest req = new AuthRequest("wrong", "wrong");

        mockMvc.perform(post("/Login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(req)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void successLogin() throws Exception {
        AuthRequest req = new AuthRequest("organizerOne", "organizer123$");

        mockMvc.perform(post("/Login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(req)))
            .andExpect(status().isOk());
    }
}
------------------------------

-------------------------------------------------------------------------------------------------------------------

package com.challenge.ecoVoteApplication;
import com.challenge.ecoVoteApplication.dto.AuthRequest;
import com.challenge. ecoVoteApplication.dto. UpdateActivityDto;
import com. challenge. ecoVoteApplication.enums. Category;
import com.challenge. ecoVoteApplication.enums.Status;
import com. challenge. ecoVoteApplication. enums. UserRoles;
import com.challenge. ecoVoteApplication.models.SustainableActivity;
import com. challenge. ecoVoteApplication.models. UserInfo;
import com. challenge. ecoVoteApplication. models. Vote;
import org. json. JSONArray;
import org.junit.jupiter.api .*;
import org. springframework.boot. test. context. SpringBootTest;
import org.junit.jupiter.api. Test;
import org. springframework.boot. test.context. SpringBootTest;
import com. fasterxml. jackson.databind. ObjectMapper;
import org. hamcrest. Matchers;
import org.json. JSONException;


@TestMethodOrder (MethodOrderer.MethodName.class)
@SpringBootTest

public class EcoVoteApplicationTests {


private MockMvc mockMvc;

public static final String TOKEN_ORGANIZER_1 = "token_organizer_1";

public static final String TOKEN_ORGANIZER_2 = "token_organizer_2";

public static final String TOKEN_CITIZEN_1 = "token_citizen_1";

public static final String TOKEN_CITIZEN_2 = "token_citizen_2";

public static final String ID_ACTIVITY_1 = "id_activity_1";

public static final String ID_ACTIVITY_2 = "id_activity_2";


@Autowired
A 12 / 3 ~
WebApplicationContext context;


@BeforeEach
void setMockMvc() { mockMvc = MockMvcBuilders. webAppContextSetup(context) .apply(springSecurity() ).build();

------------------------------------------------------------
@Test
void getAllInitiativesSuccessTest() throws Exception {

mockMvc.perform(get( udTemplate: "/activity/List")

.contentType(MediaType .APPLICATION_JSON_VALUE))

.andExpect (MockMvcResultMatchers. status ( ) . is0k())
.andExpect(jsonPath( expression: "$. [0].id", Matchers.is( value: 1)))
.andExpect(jsonPath( expression: "$. [8]. title", containsStringIgnoringCase( substring: "Community Tree Planting")))
.andExpect (jsonPath ( expression: "$. [0] . description", containsStringIgnoringCase( substring: "plant trees") ))
.andExpect(jsonPath( expression, "$. [e].category", containsStringIgnoringCase( substring: "GREEN_SPACE")))
.andExpect(jsonPath ( expression: "s. [0]. status", containsStringIgnoringCase( substring: "OPEN")))
.andExpect(jsonPath ( expression: "$. [8].estimatedCost", Matchers. is( value: 5008)))
.andExpect(jsonPath( expression: "$. [8]. Location", containsStringIgnoringCase( substring: "Central Park")))
.andExpect (jsonPath ( expression: "s. [o] . verified", Matchers. is ( value: true) ) )
.andExpect(jsonPath( expression; "$. [0] . verified", Matchers. is ( value. true) ))
.andExpect(jsonPath( expression: "$. [e].postedById", Matchers. is( value. 1)) )
.andExpect(jsonPath( expression: "$. [1].id", Matchers. is( value: 2)))
.andExpect(jsonPath ( expression; "$. [1]. title", containsStringIgnoringCase( substring "Plastic Free Challenge")))
.andExpect(jsonPath( expression: "$. [1].description", containsStringIgnoringCase( substring: "single-use plastics")))
.andExpect (jsonPath( expression: "$. [1] . category", containsStringIgnoringCase( substring: "WASTE_REDUCTION")))
.andExpect(jsonPath( expression: "$. [1]. status", containsStringIgnoringCase( substring: "OPEN")))
.andExpect(jsonPath( expression: "s. [1].estimatedCost", Matchers.is( value: 7666)))
.andExpect (jsonPath( expression: "S. [1]. Location", containsStringIgnoringCase( substring: "Online")))
.andExpect(jsonPath( expression: "$. [1] . verified", Matchers. is ( value: false) ))
.andExpect(jsonPath( expression: "$. [1].postedById", Matchers.is( value: 2)) );


-------------------------------------------------
@Test
void a_getVotesOfActivity() throws Exception{

mockMvc.perform(get( urlTemplate: "/vote/List/1")

.contentType(MediaType . APPLICATION_JSON_VALUE))

.andExpect (MockMvcResultMatchers. status ( ) . is0k())
.andExpect(jsonPath( expression: "$. [0]. id", Matchers. is( value: 3)))
.andExpect(jsonPath( expression: "$. [0]. support", Matchers. is ( value: true) ) )
.andExpect(jsonPath( expression: "$. [0].comment", containsStringIgnoringCase( substring: "I support")))
.andExpect(jsonPath( expression: "$. [8].voterId", Matchers.is( value: 5)))
.andExpect(jsonPath( expression: "$. [0]. activityId", Matchers.is( value: 2)))
.andExpect(jsonPath( expression: "$. [1].id", Matchers. is( value: 4)) )
.andExpect(jsonPath( expression: "$. [1]. support", Matchers. is ( value: false) ))
.andExpect(jsonPath( expression: "$. [1].comment", containsStringIgnoringCase( substring: "I can't")))
.andExpect(jsonPath( expression: "$. [1]. voterId", Matchers. is( value: 3)))
.andExpect(jsonPath( expression: "$. [1].activityId", Matchers.is( value: 2)) )
.andExpect(jsonPath( expression: "$. [2]. id", Matchers. is( value: 5)))
.andExpect(jsonPath( expression: "$. [2] . support", Matchers. is( value: false) ))
.andExpect(jsonPath( expression: "$. [2]. comment", containsStringIgnoringCase( substring: "I oppose")))
.andExpect(jsonPath( expression: "$. [2]. voterId", Matchers.is( value: 4)))
.andExpect(jsonPath( expression: "$. [2].activityId", Matchers.is( value: 2)));



------------------------------------------------------
@Test
2 D
void b_testFailedLoginAttempt() throws Exception{
//wrong LOGIN attempt

2
AuthRequest LoginData = new AuthRequest( usemame: "organizerOne",
password: "wrongpassword" );
mockMvc. perform(post( urlTemplate: "/Login")
.content(toJson(LoginData) ) .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest()).andReturn(
5

AuthRequest LoginData2 = new AuthRequest ( username: "wrongUser", password: "citizen123$");
mockMvc.perform(post( urlTemplate: "/Login")
.content(toJson(LoginData2)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest()).andReturn




-------------------------------------------------------------
@Test
void c_testSuccessLoginAttemptOrganizer() throws Exception{
A 12 / 3 AV
//admin SuccessLoginAttempt

AuthRequest LoginData = new AuthRequest( usemame: "organizerTwo", password: "organizer456$");

MvcResult result = mockMvc.perform(post( unTemplate: "/Login")
.content(toJson(LoginData) ) .contentType(MediaType.APPLICATION_JSON) ).andExpect(status().isOk()).andReturn();

JSONObject obj = new JSONObject(result.getResponse() .getContentAsString());

assert obj.has( s: "accessToken");
assert obj.getInt( s: "status") == 208;

saveDataToFileSystem(TOKEN_ORGANIZER_1, obj.getString( s: "accessToken"));




AuthRequest loginData1 = new AuthRequest( username: "organizerOne", password: "organizer123$");

MvcResult result1 = mockMvc.perform(post( unTemplate: "/Login")
.content(toJson(LoginData1) ) .contentType(MediaType.APPLICATION_JSON)).andExpect(status().is0k()).andReturn();

JSONObject obj1 = new JSONObject(result1.getResponse() .getContentAsString());
assert obj1. has( s; "accessToken");
assert obj.getInt( s: "status") == 200;

saveDataToFileSystem(TOKEN_ORGANIZER_2, obj1.getString( s; "accessToken"));




-----------------------------------------------------------------
@Test
void d_checkSuccessLoginAttemptCitizen() throws Exception
A 12 ¥3

//customer SuccessLoginAttempt
AuthRequest LoginData = new AuthRequest ( username:
"citizenTwo"
password: "citizen456$");

MvcResult result = mockMvc.perform(post( unTemplate: "/Login")
.content(toJson(LoginData)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().is0k()).andReturn();

JSONObject jsonUserResponse = new JSONObject(result.getResponse() .getContentAsString());
assert jsonUserResponse. has( & "accessToken");
assert jsonUserResponse.getInt( & "status") == 200;

saveDataToFileSystem(TOKEN_CITIZEN_1, jsonUserResponse.getString( s: "accessToken"));



AuthRequest LoginData1 = new AuthRequest( username: "citizenOne"
password: "citizen123$");
MvcResult result1 = mockMvc. perform(post ( udTemplate: "/login")
.content(toJson(LoginData1) ).contentType(MediaType.APPLICATION_JSON)) .andExpect(status().isOk()).andReturn();

JSONObject jsonUser1Response = new JSONObject(result1.getResponse() .getContentAsString());
assert jsonUser1Response. has( = "accessToken");
assert jsonUser1Response.getInt( s: "status") == 208;

saveDataToFileSystem(TOKEN_CITIZEN_2, jsonUser1Response.getString( s: "accessToken"));

=================================================================================





--===============================================================================================================
===================================END OF DOCUMENT
=================================================================================
=============================================================================================================
=============================================================================================================
--=======================================================================================
--=========================================================================================================

	
*/
	
	
	
	
	

}
