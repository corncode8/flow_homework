package com.flow.homework.domain.whitelist.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QWhiteList is a Querydsl query type for WhiteList
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWhiteList extends EntityPathBase<WhiteList> {

    private static final long serialVersionUID = 1950824066L;

    public static final QWhiteList whiteList = new QWhiteList("whiteList");

    public final com.flow.homework.domain.common.QBaseEntity _super = new com.flow.homework.domain.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath description = createString("description");

    public final DateTimePath<java.time.LocalDateTime> endTime = createDateTime("endTime", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath ipAddress = createString("ipAddress");

    public final DateTimePath<java.time.LocalDateTime> startTime = createDateTime("startTime", java.time.LocalDateTime.class);

    public final EnumPath<WhiteList.State> status = createEnum("status", WhiteList.State.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QWhiteList(String variable) {
        super(WhiteList.class, forVariable(variable));
    }

    public QWhiteList(Path<? extends WhiteList> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWhiteList(PathMetadata metadata) {
        super(WhiteList.class, metadata);
    }

}

