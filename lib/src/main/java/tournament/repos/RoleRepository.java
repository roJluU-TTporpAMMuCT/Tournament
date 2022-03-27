package tournament.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import tournament.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {}
