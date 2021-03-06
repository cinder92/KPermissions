/*
 * Copyright (c) 2018 Fondesa
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fondesa.kpermissions.request.runtime

import android.app.Activity
import android.app.Fragment
import android.app.FragmentManager
import android.os.Build
import android.support.annotation.RequiresApi
import com.fondesa.kpermissions.request.runtime.FragmentRuntimePermissionHandlerProvider.Companion.FRAGMENT_TAG

/**
 * Implementation of [RuntimePermissionHandler] that uses a [FragmentRuntimePermissionHandler]
 * to manage the permissions.
 *
 * The [Fragment] will be added synchronously to the [Activity] with the tag [FRAGMENT_TAG].
 * Only one [Fragment] in the same [Activity] will be instantiated to avoid
 * multiple instances.
 *
 * @property manager the [FragmentManager] of the [Activity].
 */
open class FragmentRuntimePermissionHandlerProvider(private val manager: FragmentManager) :
        RuntimePermissionHandlerProvider {

    @RequiresApi(Build.VERSION_CODES.M)
    final override fun provideHandler(): RuntimePermissionHandler {
        // Obtain the current Fragment if possible, otherwise create it.
        var fragment = manager.findFragmentByTag(FRAGMENT_TAG) as? RuntimePermissionHandler
        if (fragment == null) {
            // Create the Fragment delegated to handle permissions.
            fragment = createPermissionHandlerFragment()
            val transaction = manager.beginTransaction()
                    .add(fragment, FRAGMENT_TAG)

            // Commit the fragment synchronously.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                transaction.commitNowAllowingStateLoss()
            } else {
                transaction.commitAllowingStateLoss()
                manager.executePendingTransactions()
            }
        }
        return fragment
    }

    @RequiresApi(Build.VERSION_CODES.M)
    protected open fun createPermissionHandlerFragment(): FragmentRuntimePermissionHandler =
            DefaultFragmentRuntimePermissionHandler()

    companion object {
        private const val FRAGMENT_TAG = "KPermissionsFragment"
    }
}