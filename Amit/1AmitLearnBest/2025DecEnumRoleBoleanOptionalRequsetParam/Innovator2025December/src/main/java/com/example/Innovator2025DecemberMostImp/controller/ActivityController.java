package com.example.Innovator2025DecemberMostImp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Innovator2025DecemberMostImp.dto.UpdateActivityDto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@RestController
@RequestMapping("/activity")
public class ActivityController {
	
	/*
========================
	6. ACCESS RULES
	========================
	
	Public → No auth
	Organizer → ORGANIZER role
	Citizen → CITIZEN role
	Owner → Organizer + creator
	
	Failure → 403
	
	========================
	7. ENDPOINTS
	========================
	
	GET /activity/List
	POST /activity/add
	PATCH /activity/update/{id}
	DELETE /activity/delete/{id}
	
	IMPORTANT:
	Case-sensitive endpoints
	
	========================
------------------------------------------------------------------------------------------------
GET ACTIVITIES
GET /activity/List

Responses:
200 OK
204 No Content


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


-----------------------------------------------------------------------------------------------
ADD ACTIVITY (Organizer only)
POST /activity/add

Responses:
201 Created
401 Unauthorized
403 Forbidden

/activity/add
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

---------------------------------------------------------------------------------------------
UPDATE ACTIVITY
PATCH /activity/update/{id}

Request:
{
  "status": "CLOSED"
}

Responses:
200 OK
400 Bad Request
403 Forbidden

PATCH METHOD . (activity/update/[id]
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
----------------------------------------------------------------------------------------
 DELETE ACTIVITY
DELETE /activity/delete/{id}

Responses:
204 Deleted
400 Not Found
403 Forbidden

DELETE METHOD - /activity/delete/[id)

Note: This Method requires authentication. User who was authenticated and have rode "ORGANIZER" and creator of the given id object,
 can be able to access this endpoint.

Get the activity data object with given id from sustainable activity model, delete it and return "deleted successfully" with status
 code 204.

If the given id is not found, then return "not found" with status code 400.

If the authenticated user is not a creator of the given id object, then return "you don't have permission" with status code 403.

---------------------------

	 */

	@PostMapping("/List")
	public ResponseEntity<Object> getActivity(){
		return null;
	}
	
	@GetMapping("/add")
	public ResponseEntity<Object> addActivity(){
		return null;
	}
	
	@PatchMapping("/update/{id}")
	public ResponseEntity<Object> updateActivity(int id, UpdateActivityDto activityDto){
		//vry important here in update they have created new Activity DTO
		//here they have attribute status
		//status is ENUM so need to check if this should be written ot not:
		//@Enumerated(EnumType.ORDINAL)
		//My code was giving forbidden as in update any Organizer can update no constraint they had given there
		//i did not check dto there if anything was there
		return null;
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Object> deleteActivity(int id){
		
		return null;
	}
	
	
}
