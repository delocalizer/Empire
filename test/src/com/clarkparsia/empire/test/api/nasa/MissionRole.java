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

package com.clarkparsia.empire.test.api.nasa;

import com.clarkparsia.empire.annotation.Namespaces;
import com.clarkparsia.empire.annotation.RdfsClass;
import com.clarkparsia.empire.annotation.RdfProperty;
import com.clarkparsia.empire.annotation.RdfId;
import com.clarkparsia.empire.annotation.NamedGraph;
import com.clarkparsia.empire.test.api.BaseTestClass;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;

import java.net.URI;

import com.clarkparsia.utils.BasicUtils;

/**
 * <p></p>
 *
 * @author Michael Grove
 */
@NamedQuery(name="sovietSpacecraftSPARQL",
			query="where { ?result <http://purl.org/net/schemas/space/agency> \"U.S.S.R\" }")

@Namespaces({"", "http://purl.org/net/schemas/space/",
			 "foaf", "http://xmlns.com/foaf/0.1/",
			 "dc", "http://purl.org/dc/elements/1.1/"})
@Entity
@RdfsClass("MissionRole")
@NamedGraph(type = NamedGraph.NamedGraphType.Instance)
public class MissionRole extends BaseTestClass {
	@RdfProperty("role")
	private URI role;

	@RdfProperty("mission")
	private Mission mission;

	@RdfId
	@RdfProperty("rdfs:label")
	private String label;

	@RdfProperty("actor")
	private FoafPerson actor;

	public URI getRole() {
		return role;
	}

	public void setRole(final URI theRole) {
		role = theRole;
	}

	public Mission getIssion() {
		return mission;
	}

	public void setIssion(final Mission theIssion) {
		mission = theIssion;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(final String theLabel) {
		label = theLabel;
	}

	public FoafPerson getActor() {
		return actor;
	}

	public void setActor(final FoafPerson theActor) {
		actor = theActor;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		final MissionRole that = (MissionRole) o;

		if (actor != null ? !actor.equals(that.actor) : that.actor != null) {
			return false;
		}
		if (label != null ? !label.equals(that.label) : that.label != null) {
			return false;
		}
		if (mission != null ? !mission.equals(that.mission) : that.mission != null) {
			return false;
		}
		if (role != null ? !role.equals(that.role) : that.role != null) {
			return false;
		}
		if (!BasicUtils.equalsOrNull(getRdfId(), that.getRdfId())) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int aresult = role != null ? role.hashCode() : 0;
		aresult = 31 * aresult + (mission != null ? mission.hashCode() : 0);
		aresult = 31 * aresult + (label != null ? label.hashCode() : 0);
		aresult = 31 * aresult + (actor != null ? actor.hashCode() : 0);
		return aresult;
	}
}
