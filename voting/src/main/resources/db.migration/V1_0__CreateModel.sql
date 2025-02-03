CREATE TABLE `topics_entity` (
  `id` binary(16) NOT NULL,
  `duration` int DEFAULT NULL,
  `effective_end_time` datetime(6) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `open` bit(1) NOT NULL,
  `predicted_end_time` datetime(6) DEFAULT NULL,
  `start_time` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE `votes_entity` (
  `id` binary(16) NOT NULL,
  `cpf` varchar(255) DEFAULT NULL,
  `option_vote` bit(1) NOT NULL,
  `topic_id` binary(16) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `topic_foreign_key` (`topic_id`),
  FOREIGN KEY (`topic_id`) REFERENCES `topics_entity` (`id`)
) ENGINE=InnoDB;