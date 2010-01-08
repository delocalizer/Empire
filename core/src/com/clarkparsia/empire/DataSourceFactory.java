/*
 * Copyright (c) 2009-2010 Clark & Parsia, LLC. <http://www.clarkparsia.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.clarkparsia.empire;

import com.clarkparsia.empire.DataSource;

import java.util.Map;

/**
 * <p>Factory interface for creating instances of a {@link DataSource}</p>
 *
 * @author Michael Grove <mike@clarkparsia.com>
 * @see DataSource
 * @since 0.1
 */
public interface DataSourceFactory {

	/**
	 * Returns whether or not a {@link com.clarkparsia.empire.DataSource} can be created by this factory for the given set of data.
	 * @param theMap the data to use to create the data source
	 * @return true if this can create a data source, false otherwise
	 */
	public boolean canCreate(Map<String, String> theMap);

	/**
	 * Create a {@link DataSource} from the given parameters
	 * @param theMap the parameters
	 * @return a new DataSource
	 * @throws DataSourceException thrown if the Map contains insufficient data, the data source cannot be constructed
	 * from the data, or if there was an error establishing a connection to the data source.
	 */
	public DataSource create(Map<String, String> theMap) throws DataSourceException;
}
