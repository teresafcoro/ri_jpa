package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.repository.ClientRepository;
import uo.ri.cws.domain.Client;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;

public class ClientJpaRepository extends BaseJpaRepository<Client>
	implements ClientRepository {

    @Override
    public Optional<Client> findByDni(String dni) {
	throw new RuntimeException("Not yet implemented");
    }

    @Override
    public List<Client> findSponsoredByClient(String id) {
	throw new RuntimeException("Not yet implemented");
    }

    @Override
    public List<Client> findRecomendedBy(String id) {
	throw new RuntimeException("Not yet implemented");
    }

    @Override
    public List<Client> findWithThreeUnusedWorkOrders() {
	throw new RuntimeException("Not yet implemented");
    }

    @Override
    public List<Client> findWithRecomendationsDone() {
	throw new RuntimeException("Not yet implemented");
    }

}
