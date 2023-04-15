package project.mainframe.api.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.mainframe.api.project.entities.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
