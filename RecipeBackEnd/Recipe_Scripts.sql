/*drop table Recipe_directory.Recipe*/
/*select * from Recipe_directory.Recipe for update;*/
create database if not exists `Recipe_directory`; use `Recipe_directory`;
create table Recipe (
`id` int not null auto_increment,
`name` varchar(1000) default null,
`description` varchar(4000) default null,
`created_date` datetime default null,
`updated_date` datetime default null,
`created_by` varchar(50) default null,
`servings` int default 0,
`cooking_time` varchar(50) default null,
`rating` int default 0,
primary key (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
CREATE TABLE `ingredients` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `recipe_id` int(11) NOT NULL,
  `name` varchar(1000) DEFAULT NULL,
  `quantity` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `recipe_id` (`recipe_id`),
  CONSTRAINT `ingredients_ibfk_1` FOREIGN KEY (`recipe_id`) REFERENCES `recipe` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;

CREATE TABLE `instructions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `recipe_id` int(11) NOT NULL,
  `step` varchar(4000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `recipe_id` (`recipe_id`),
  CONSTRAINT `instructions_ibfk_1` FOREIGN KEY (`recipe_id`) REFERENCES `recipe` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=latin1;

create database if not exists `user_directory`; use `user_directory`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(4000) NOT NULL,
  `password` varchar(4000) NOT NULL,
  PRIMARY KEY (`id`)
  ) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=latin1;