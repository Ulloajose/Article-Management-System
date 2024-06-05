create table article
(
    id               int GENERATED ALWAYS AS IDENTITY   ,
    author           varchar(150) not null,
    comment          text,
    object_id        varchar(150),
    title            varchar(150),
    url              varchar(250),

    created_date    timestamp,
    last_modified_date     timestamp,
    created_by     integer,
    last_modified_by      integer,
    deleted   boolean  default  false,
    primary key (id),
    constraint article_fk_created_by
        foreign key (created_by) references app_user (id),
    constraint article_fk_last_modified_by
        foreign key (last_modified_by) references  app_user(id)
);

create table article_tag
(
    id   int GENERATED ALWAYS AS IDENTITY,
    name varchar(64) not null,
    article_id     integer,
    created_date    timestamp,
    last_modified_date     timestamp,
    created_by     integer,
    last_modified_by      integer,
    deleted   boolean  default  false,
    primary key (id),
    constraint article_tag_fk_article_id
        foreign key (article_id) references article (id),
    constraint article_tag_fk_created_by
        foreign key (created_by) references app_user (id),
    constraint article_tag_fk_last_modified_by
        foreign key (last_modified_by) references app_user (id)
);
