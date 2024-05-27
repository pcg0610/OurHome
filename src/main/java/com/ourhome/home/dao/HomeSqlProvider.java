package com.ourhome.home.dao;

import org.apache.ibatis.jdbc.SQL;

import com.ourhome.home.entity.SearchCondition;

public class HomeSqlProvider {

	public String selectHomes(SearchCondition searchCondition) {
		return new SQL() {{
			if (searchCondition.getUserId() == 0) { 
				SELECT ("h.*, false AS is_favorite");
			} else {
				SELECT("h.*, IF(fh.registered_date IS NULL, FALSE, TRUE) AS is_favorite");				
			}
			FROM("home h");
			
			if (searchCondition.getUserId() != 0) {
				LEFT_OUTER_JOIN("favorite_home fh ON h.id = fh.home_id");
				LEFT_OUTER_JOIN("user u ON fh.user_id = u.id");
			}
			
			StringBuilder whereClause = new StringBuilder();
			
			if (searchCondition.getAddress() != null &&
					!searchCondition.getAddress().equals("")) {
				whereClause.append("address LIKE CONCAT('%', #{address}, '%') ").append(searchCondition.getMatchTypeName()).append(" ");
			}
			
			if (searchCondition.getName() != null && 
					!searchCondition.getName().equals("")) {
				whereClause.append("name LIKE CONCAT('%', #{name}, '%') ").append(searchCondition.getMatchTypeName()).append(" ");
			}
			
			if (searchCondition.getMinLat() != 0 && searchCondition.getMaxLat() != 0 && searchCondition.getMinLng() != 0 && searchCondition.getMaxLng() != 0) {
				whereClause.append("lng > #{minLng} AND lng < #{maxLng} AND lat > #{minLat} ANd lat < #{maxLat} ").append(searchCondition.getMatchTypeName()).append(" ");
			}
			
			if (whereClause.length() > 0) {
				int lastIndex = whereClause.lastIndexOf(searchCondition.getMatchTypeName());
                whereClause.setLength(lastIndex - 1);  // Remove the last conditionType and the space before it
                WHERE(whereClause.toString());
			}
			
			if (searchCondition.getLimit() > 0) {
				LIMIT(searchCondition.getLimit());
			}
		}}.toString();
	}
}
