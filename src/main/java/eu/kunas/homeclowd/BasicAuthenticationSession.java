/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package eu.kunas.homeclowd;

import eu.kunas.homeclowd.model.HCUserEntity;
import eu.kunas.homeclowd.service.UserServiceImpl;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.request.Request;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class BasicAuthenticationSession extends AuthenticatedWebSession {

	@SpringBean
	private UserServiceImpl userService;

	private HCUserEntity userEntity;

	public BasicAuthenticationSession(Request request) {
		super(request);
		injectDependencies();
	}

	public HCUserEntity getUserEntity(){
		return userEntity;
	}


	private void injectDependencies() {
		Injector.get().inject(this);
	}

	@Override
	public boolean authenticate(String username, String password) {
		userEntity =  userService.getUser(username,password);
		if(userEntity != null){
			return true;
		}
		return false;
	}

	@Override
	public Roles getRoles() {
		if (isSignedIn()) {
			Roles roles = new Roles();
			roles.add(userEntity.getRole());
			return roles;
		}
		return null;
	}

}
