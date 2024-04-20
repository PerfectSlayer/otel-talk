### How to instrument your Java applications using OpenTemetry -- Devoxx FR edition

### You missed it?

You can find [the slides of the presentation on Speaker Deck](https://speakerdeck.com/perfectslayer/instrumentez-vos-applications-java-avec-opentelemetry).

#### To run the Datadog agent as OTel collector

Ensure to export `DD_API_KEY` then use the provided script:
```shell
export DD_API_KEY=<secret_api_key>
./run-agent.sh
```

Test the service endpoints using the `request.http` file and check the trace using [the Datadog backend](https://app.datadoghq.eu/account/login).
