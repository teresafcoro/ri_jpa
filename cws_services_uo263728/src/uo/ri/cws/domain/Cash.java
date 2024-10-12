package uo.ri.cws.domain;

import uo.ri.util.assertion.ArgumentChecks;

public class Cash extends PaymentMean {

    public Cash() {
    }

    public Cash(Client client) {
	ArgumentChecks.isNotNull(client);

	Associations.Pay.link(client, this);
    }

    @Override
    public String toString() {
	return "Cash [getAccumulated()=" + getAccumulated() + ", getClient()="
		+ getClient() + "]";
    }

}
