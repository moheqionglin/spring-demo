package com.moheqionglin;

import pl.allegro.tech.embeddedelasticsearch.EmbeddedElastic;
import pl.allegro.tech.embeddedelasticsearch.PopularProperties;

import java.io.IOException;

public class EmbedElasticsearch {

    public static void main(String[] args) throws IOException, InterruptedException {
        EmbeddedElastic embeddedElastic = EmbeddedElastic.builder()
                .withElasticVersion("5.5.2")
                .withSetting(PopularProperties.HTTP_PORT, 21121)
                .build();
        embeddedElastic.start();
    }

}
