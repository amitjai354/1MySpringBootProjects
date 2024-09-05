package repository;

import org.springframework.data.jpa.repository.JpaRepository;

import entity.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer>{

}
