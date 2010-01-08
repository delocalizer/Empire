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

package com.clarkparsia.empire.impl;

import org.openrdf.model.Graph;
import com.clarkparsia.empire.DataSource;
import com.clarkparsia.empire.DataSourceException;
import com.clarkparsia.empire.MutableDataSource;
import com.clarkparsia.empire.QueryException;
import com.clarkparsia.empire.QueryFactory;
import com.clarkparsia.empire.ResultSet;
import com.clarkparsia.empire.SupportsTransactions;

import java.net.URI;
import java.net.ConnectException;

import com.clarkparsia.sesame.utils.ExtendedGraph;

/**
 * <p><b>Very</b> simple transactional support to put on top of a database that does not already support it.
 * We do the operations live on the database, but keep a track of what was added or deleted so on rollback we can
 * try and undo the edits.  If the rollback fails, it very well could have failed for part of the rollback
 * and you are left with an inconsistent database.  For real transactional support, use a database that supports it.</p>
 *
 * @author Michael Grove
 * @since 0.1
 */
public class TransactionalDataSource implements DataSource, MutableDataSource, SupportsTransactions {

	/**
	 * The DataSource the operations will be applied to
	 */
	private MutableDataSource mDataSource;

	/**
	 * The set of triples that have been removed in the current transaction
	 */
	private ExtendedGraph mRemoveGraph;

	/**
	 * The set of triples that have been added in the current transaction
	 */
	private ExtendedGraph mAddGraph;

	/**
	 * Whether or not a transaction is currently active
	 */
	private boolean mIsInTransaction;

	/**
	 * @inheritDoc
	 */
	protected TransactionalDataSource(final MutableDataSource theDataSource) {
		mDataSource = theDataSource;

		mRemoveGraph = new ExtendedGraph();
		mAddGraph = new ExtendedGraph();
	}

	/**
	 * @inheritDoc
	 */
	public void begin() throws DataSourceException {
		assertNotInTransaction();

		mIsInTransaction = true;

		mRemoveGraph.clear();
		mAddGraph.clear();
	}

	/**
	 * @inheritDoc
	 */
	public void commit() throws DataSourceException {
		assertInTransaction();

		mIsInTransaction = false;

		mRemoveGraph.clear();
		mAddGraph.clear();
	}

	/**
	 * @inheritDoc
	 */
	public void rollback() throws DataSourceException {
		assertInTransaction();

		try {
			if (mRemoveGraph.numStatements() > 0) {
				mDataSource.add(mRemoveGraph);
			}

			if (mAddGraph.numStatements() > 0) {
				mDataSource.remove(mAddGraph);
			}
		}
		catch (DataSourceException e) {
			throw new DataSourceException("Rollback failed, database is likely to be in an inconsistent state.", e);
		}
		finally {
			mIsInTransaction = false;

			mRemoveGraph.clear();
			mAddGraph.clear();
		}
	}

	/**
	 * @inheritDoc
	 */
	public void add(final Graph theGraph) throws DataSourceException {
		mAddGraph.add(theGraph);
		mDataSource.add(theGraph);
	}

	/**
	 * @inheritDoc
	 */
	public void remove(final Graph theGraph) throws DataSourceException {
		mRemoveGraph.add(theGraph);
		mDataSource.remove(theGraph);
	}

	/**
	 * @inheritDoc
	 */
	public boolean isConnected() {
		return mDataSource.isConnected();
	}

	/**
	 * @inheritDoc
	 */
	public void connect() throws ConnectException {
		mDataSource.connect();
	}

	/**
	 * @inheritDoc
	 */
	public void disconnect() {
		mDataSource.disconnect();
	}

	/**
	 * @inheritDoc
	 */
	public ResultSet selectQuery(final String theQuery) throws QueryException {
		return mDataSource.selectQuery(theQuery);
	}

	/**
	 * @inheritDoc
	 */
	public Graph graphQuery(final String theQuery) throws QueryException {
		return mDataSource.graphQuery(theQuery);
	}

	/**
	 * @inheritDoc
	 */
	public Graph describe(final URI theURI) throws DataSourceException {
		return mDataSource.describe(theURI);
	}

	/**
	 * @inheritDoc
	 */
	public QueryFactory getQueryFactory() {
		return mDataSource.getQueryFactory();
	}

	/**
	 * Return whether or not this data source is in a transaction
	 * @return true if it is in a transaction, false otherwise
	 */
	public boolean isInTransaction() {
		return mIsInTransaction;
	}

	/**
	 * Asserts that this DataSource should not be in a transaction
	 * @throws com.clarkparsia.empire.DataSourceException thrown if the data source is in a transaction
	 */
	private void assertNotInTransaction() throws DataSourceException {
		if (isInTransaction()) {
			throw new DataSourceException("Cannot complete action, currently in a transaction");
		}
	}

	/**
	 * Asserts that this DataSource should be in a transaction
	 * @throws DataSourceException thrown if the data source is not in a transaction
	 */
	private void assertInTransaction() throws DataSourceException {
		if (!isInTransaction()) {
			throw new DataSourceException("Cannot complete action, not in a transaction");
		}
	}
}
