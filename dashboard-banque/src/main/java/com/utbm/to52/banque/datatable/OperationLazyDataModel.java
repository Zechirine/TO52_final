/*
 * Copyright 2020 JoinFaces.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.utbm.to52.banque.datatable;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.utbm.to52.banque.entities.Operation;
import com.utbm.to52.banque.service.IBanqueMetier;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 *
 * @author kommit
 */
public class OperationLazyDataModel extends LazyDataModel<Operation>{
    private static final long serialVersionUID = -6123945723069023025L;
	private final transient IBanqueMetier service;
	private static final SortOrder DEFAULT_SORT_ORDER = SortOrder.ASCENDING;
	private static final String DEFAULT_SORT_FIELD = "numeroOperation";
        
        

	public OperationLazyDataModel(IBanqueMetier service) {
		this.service = service;
	}
	
	@Override
	public Object getRowKey(Operation operation) {
		return operation.getNumeroOperation();
	}
	
	@Override
	public Operation getRowData(String rowKey) {
		Long rowId = Long.valueOf(rowKey);
		List<Operation> operations = (List<Operation>) super.getWrappedData();
		return operations.stream().filter(operation -> operation.getNumeroOperation().equals(rowId)).findAny().orElse(null);
	}

	@Override
	public List<Operation> load(int first, int pageSize, List<SortMeta> multiSortMeta, Map<String, Object> filters) {
		          Sort sort = new Sort(getDirection(DEFAULT_SORT_ORDER), DEFAULT_SORT_FIELD);
		if (multiSortMeta != null) {
			List<Sort.Order> orders = multiSortMeta.stream()
					.map(m -> new Sort.Order(getDirection(m.getSortOrder() != null ? m.getSortOrder() : DEFAULT_SORT_ORDER),
							m.getSortField()))
					.collect(Collectors.toList());
			sort = new Sort(orders);
		}
		return filterAndSort(first, pageSize, filters, sort);
	}

	@Override
	public List<Operation> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		Sort sort = null;
		if (sortField != null) {
			sort = new Sort(getDirection(sortOrder != null ? sortOrder : DEFAULT_SORT_ORDER), sortField);
		} else if (DEFAULT_SORT_FIELD != null) {
			sort = new Sort(getDirection(sortOrder != null ? sortOrder : DEFAULT_SORT_ORDER), DEFAULT_SORT_FIELD);
		}
		return filterAndSort(first, pageSize, filters, sort);
	}
	
	private List<Operation> filterAndSort(int first, int pageSize, Map<String, Object> filters, Sort sort) {
		Map<String, String> filtersMap = filters.entrySet().stream()
				.collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().toString()));
		          Page<Operation> page = service.findByFilter(filtersMap, new PageRequest(first / pageSize, pageSize, sort));
		this.setRowCount(((Number) page.getTotalElements()).intValue());
		this.setWrappedData(page.getContent());
		return page.getContent();
	}

	private static Sort.Direction getDirection(SortOrder order) {
		switch (order) {
		case ASCENDING:
			return Sort.Direction.ASC;
		case DESCENDING:
			return Sort.Direction.DESC;
		case UNSORTED:
		default:
			return null;
		}
	}
}
