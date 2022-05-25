    -- 清空当天数据
    DELETE FROM LC_CN_ADDRESS_DC_DAILY WHERE DAY = '20200902';
    -- 插入当前数据
    INSERT INTO LC_CN_ADDRESS_DC_DAILY ( DAY,	CODE,	NAME,	ADDRESS_TYPE,	PARENT_CODE,	ADDRESS, IS_LEAF, DC1, DC2, DC3, OBJECT_VERSION_NUMBER, CREATED_BY, CREATION_DATE, LAST_UPDATED_BY, LAST_UPDATE_DATE ) 
      SELECT '20200902', CODE, NAME, ADDRESS_TYPE, PARENT_CODE, ADDRESS, LEAF_FLAG, NULL, NULL, NULL, 1, 'ADMIN', NOW(), 'ADMIN', NOW() 
        FROM LC_CN_ADDRESS WHERE DELETED_FLAG = 0 AND (INACTIVE_DATE > STR_TO_DATE('20200902','%Y%m%d') OR INACTIVE_DATE IS NULL );

    -- 删除失效子数据
    DELETE a FROM LC_CN_ADDRESS_DC_DAILY a WHERE a.DAY = '20200902'
        AND a.ADDRESS_TYPE = 2 AND a.PARENT_CODE NOT IN (
        SELECT b.CODE FROM (SELECT * FROM LC_CN_ADDRESS_DC_DAILY c WHERE c.DAY = '20200902') b
        );
    DELETE a FROM LC_CN_ADDRESS_DC_DAILY a WHERE a.DAY = '20200902'
        AND a.ADDRESS_TYPE = 3 AND a.PARENT_CODE NOT IN (
        SELECT b.CODE FROM (SELECT * FROM LC_CN_ADDRESS_DC_DAILY c WHERE c.DAY = '20200902') b
        );
    DELETE a FROM LC_CN_ADDRESS_DC_DAILY a WHERE a.DAY = '20200902'
        AND a.ADDRESS_TYPE = 4 AND a.PARENT_CODE NOT IN (
        SELECT b.CODE FROM (SELECT * FROM LC_CN_ADDRESS_DC_DAILY c WHERE c.DAY = '20200902') b
        );
    DELETE a FROM LC_CN_ADDRESS_DC_DAILY a WHERE a.DAY = '20200902'
        AND a.ADDRESS_TYPE = 5 AND a.PARENT_CODE NOT IN (
        SELECT b.CODE FROM (SELECT * FROM LC_CN_ADDRESS_DC_DAILY c WHERE c.DAY = '20200902') b
        );

    -- 更新DC数据
    UPDATE LC_CN_ADDRESS_DC_DAILY a
        INNER JOIN (SELECT ADDRESS_CODE, WAREHOUSE_LEVEL1 s_dc1, WAREHOUSE_LEVEL2 s_dc2, WAREHOUSE_LEVEL3 s_dc3 
        FROM lc_store_dc_setting
        WHERE (INACTIVE_DATE > STR_TO_DATE('20200902','%Y%m%d') OR INACTIVE_DATE is null )
            AND EFFECTIVE_DATE < STR_TO_DATE('20200902','%Y%m%d') 
        ) b ON a.CODE = b.ADDRESS_CODE
        SET a.DC1 = s_dc1, a.DC2 = s_dc2, a.DC3 = s_dc3
        WHERE a.DAY = '20200902';

    -- 更新子节点数据
    UPDATE LC_CN_ADDRESS_DC_DAILY a 
        INNER JOIN (SELECT * FROM LC_CN_ADDRESS_DC_DAILY) b
        ON a.PARENT_CODE = b.CODE and a.DAY = b.DAY
        SET a.DC1 = b.DC1, a.DC2 = b.DC2, a.DC3 = b.DC3
        WHERE a.ADDRESS_TYPE = 2 AND a.DAY = '20200902' AND a.DC1 IS NULL;

    UPDATE LC_CN_ADDRESS_DC_DAILY a 
        INNER JOIN (SELECT * FROM LC_CN_ADDRESS_DC_DAILY) b
        ON a.PARENT_CODE = b.CODE and a.DAY = b.DAY
        SET a.DC1 = b.DC1, a.DC2 = b.DC2, a.DC3 = b.DC3
        WHERE a.ADDRESS_TYPE = 3 AND a.DAY = '20200902' AND a.DC1 IS NULL;

    UPDATE LC_CN_ADDRESS_DC_DAILY a 
        INNER JOIN (SELECT * FROM LC_CN_ADDRESS_DC_DAILY) b
        ON a.PARENT_CODE = b.CODE and a.DAY = b.DAY
        SET a.DC1 = b.DC1, a.DC2 = b.DC2, a.DC3 = b.DC3
        WHERE a.ADDRESS_TYPE = 4 AND a.DAY = '20200902' AND a.DC1 IS NULL;

    UPDATE LC_CN_ADDRESS_DC_DAILY a 
        INNER JOIN (SELECT * FROM LC_CN_ADDRESS_DC_DAILY) b
        ON a.PARENT_CODE = b.CODE and a.DAY = b.DAY
        SET a.DC1 = b.DC1, a.DC2 = b.DC2, a.DC3 = b.DC3
        WHERE a.ADDRESS_TYPE = 5 AND a.DAY = '20200902' AND a.DC1 IS NULL;

    -- 查询未映射DC列表
    SELECT * FROM LC_CN_ADDRESS_DC_DAILY WHERE CODE <> '1' AND DC1 IS NULL AND DAY = '20200902' ORDER BY CODE;