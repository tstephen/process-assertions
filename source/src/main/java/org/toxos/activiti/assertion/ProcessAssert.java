/*******************************************************************************
 * Copyright 2014 Tiese Barrell
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.toxos.activiti.assertion;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.toxos.activiti.assertion.internal.AssertFactory;
import org.toxos.activiti.assertion.internal.AssertFactoryImpl;
import org.toxos.activiti.assertion.internal.AssertUtils;
import org.toxos.activiti.assertion.internal.EndEventAssertable;
import org.toxos.activiti.assertion.internal.HistoricVariableInstanceAssertable;
import org.toxos.activiti.assertion.internal.ProcessInstanceAssertable;
import org.toxos.activiti.assertion.internal.TaskInstanceAssertable;
import org.toxos.activiti.assertion.internal.Validate;

/**
 * Provides assertions for integration test cases that execute processes with Activiti.
 * 
 * @author Tiese Barrell
 */
public final class ProcessAssert extends AbstractProcessAssert {

    static ProcessAssertConfiguration configuration;

    static AssertFactory assertFactory = new AssertFactoryImpl();

    private ProcessAssert() {
        super();
    }

    /**
     * Sets the active configuration to the one provided.
     * 
     * @param configuration
     *            the configuration to be set
     */
    public static final void setConfiguration(final ProcessAssertConfiguration configuration) {
        ProcessAssert.configuration = configuration;
        flush();
    }

    /**
     * Gets the currently active configuration.
     * 
     * @return the configuration
     */
    public static final ProcessAssertConfiguration getConfiguration() {
        initializeConfiguration();
        return configuration;
    }

    //
    // Assertions for active process instances
    //

    /**
     * Asserts the provided process instance is active, i.e. the process instance is not ended.
     * 
     * @param processInstance
     *            the process instance to check for. May not be <code>null</code>
     */
    public static final void assertProcessActive(final ProcessInstance processInstance) {
        Validate.notNull(processInstance);
        assertProcessActive(processInstance.getId());
    }

    /**
     * Asserts the process instance with the provided id is active, i.e. the process instance is not ended.
     * 
     * @param processInstanceId
     *            the process instance's id to check for. May not be <code>null</code>
     */
    public static final void assertProcessActive(final String processInstanceId) {
        Validate.notNull(processInstanceId);
        debug(LogMessage.PROCESS_1, processInstanceId);
        try {
            getProcessInstanceAssertable().processIsActive(processInstanceId);
        } catch (final AssertionError ae) {
            fail(LogMessage.ERROR_PROCESS_1, processInstanceId);
        }
    }

    //
    // Assertions for ended process instances
    //

    /**
     * Asserts the provided process instance is ended.
     * 
     * @param processInstance
     *            the process instance to check for. May not be <code>null</code>
     */
    public static final void assertProcessEnded(final ProcessInstance processInstance) {
        Validate.notNull(processInstance);
        assertProcessEnded(processInstance.getId());
    }

    /**
     * Asserts the process instance with the provided id is ended.
     * 
     * @param processInstanceId
     *            the process instance's id to check for. May not be <code>null</code>
     */
    public static final void assertProcessEnded(final String processInstanceId) {
        Validate.notNull(processInstanceId);
        debug(LogMessage.PROCESS_5, processInstanceId);
        try {
            getProcessInstanceAssertable().processIsEnded(processInstanceId);
        } catch (final AssertionError ae) {
            fail(LogMessage.ERROR_PROCESS_2, processInstanceId);
        }
    }

    //
    // Assertions for processes in activities
    //

    /**
     * Asserts the provided process instance is active and in (at lease) an activity with the provided id.
     * 
     * @param processInstance
     *            the process instance to check for. May not be <code>null</code>
     * @param activityId
     *            the activity's id to check for. May not be <code>null</code>
     */
    public static final void assertProcessInActivity(final ProcessInstance processInstance, final String activityId) {
        Validate.notNull(processInstance);
        Validate.notNull(activityId);
        assertProcessInActivity(processInstance.getId(), activityId);
    }

    /**
     * Asserts the process instance with the provided id is active and in (at lease) an activity with the provided id.
     * 
     * @param processInstanceId
     *            the process instance's id to check for. May not be <code>null</code>
     * @param activityId
     *            the activity's id to check for. May not be <code>null</code>
     */
    public static final void assertProcessInActivity(final String processInstanceId, final String activityId) {
        Validate.notNull(processInstanceId);
        Validate.notNull(activityId);
        debug(LogMessage.PROCESS_15, processInstanceId, activityId);
        try {
            getProcessInstanceAssertable().processIsInActivity(processInstanceId, activityId);
        } catch (final AssertionError ae) {
            fail(LogMessage.ERROR_PROCESS_6, processInstanceId, activityId);
        }
    }

    //
    // Assertions for tasks
    //

    /**
     * Asserts the provided task is pending completion.
     * 
     * @param task
     *            the task to check for. May not be <code>null</code>
     */
    public static final void assertTaskUncompleted(final Task task) {
        Validate.notNull(task);
        assertTaskUncompleted(task.getId());
    }

    /**
     * Asserts the task with the provided id is pending completion.
     * 
     * @param taskId
     *            the task's id to check for. May not be <code>null</code>
     */
    public static final void assertTaskUncompleted(final String taskId) {
        Validate.notNull(taskId);

        debug(LogMessage.TASK_2, taskId);
        try {
            getTaskInstanceAssertable().taskIsUncompleted(taskId);
        } catch (final AssertionError ae) {
            fail(LogMessage.ERROR_TASK_2, taskId);
        }
    }

    /**
     * Asserts a task with the provided taskDefinitionKey is pending completion in the provided process instance.
     * 
     * @param processInstance
     *            the process instance to check for. May not be <code>null</code>
     * @param taskDefinitionKey
     *            the task's definition key to check for. May not be <code>null</code>
     */
    public static final void assertTaskUncompleted(final ProcessInstance processInstance, final String taskDefinitionKey) {
        Validate.notNull(processInstance);
        Validate.notNull(taskDefinitionKey);
        assertTaskUncompleted(processInstance.getId(), taskDefinitionKey);
    }

    /**
     * Asserts a task with the provided taskDefinitionKey is pending completion in the process instance with the
     * provided processInstanceId.
     * 
     * @param processInstanceId
     *            the process instance's id to check for. May not be <code>null</code>
     * @param taskDefinitionKey
     *            the task's definition key to check for. May not be <code>null</code>
     */
    public static final void assertTaskUncompleted(final String processInstanceId, final String taskDefinitionKey) {
        Validate.notNull(processInstanceId);
        Validate.notNull(taskDefinitionKey);

        debug(LogMessage.TASK_1, taskDefinitionKey, processInstanceId);
        try {
            getTaskInstanceAssertable().taskIsUncompleted(processInstanceId, taskDefinitionKey);
        } catch (final AssertionError ae) {
            fail(LogMessage.ERROR_TASK_1, taskDefinitionKey, processInstanceId);
        }
    }

    //
    // Assertions for ended process instances and end events
    //

    /**
     * Asserts the provided process instance is ended and has reached <strong>only</strong> the end event with the
     * provided id.
     * 
     * <p>
     * <strong>Note:</strong> this assertion should be used for processes that have exclusive end events and no
     * intermediate end events. This generally only applies to simple processes. If the process definition branches into
     * non-exclusive branches with distinct end events or the process potentially has multiple end events that are
     * reached, this method should not be used.
     * 
     * <p>
     * To test that a process ended in an end event and that particular end event was the final event reached, use
     * {@link #assertProcessEndedAndReachedEndStateLast(ProcessInstance, String)} instead.
     * 
     * <p>
     * To assert that a process ended in an exact set of end events, use
     * {@link #assertProcessEndedAndInEndEvents(ProcessInstance, String...)} instead.
     * 
     * @see #assertProcessEndedAndReachedEndStateLast(ProcessInstance, String)
     * @see #assertProcessEndedAndInEndEvents(ProcessInstance, String...)
     * 
     * @param processInstance
     *            the process instance to check for. May not be <code>null</code>
     * @param endEventId
     *            the end event's id to check for. May not be <code>null</code>
     */
    public static final void assertProcessEndedAndInExclusiveEndEvent(final ProcessInstance processInstance, final String endEventId) {
        Validate.notNull(processInstance);
        Validate.notNull(endEventId);
        assertProcessEndedAndInExclusiveEndEvent(processInstance.getId(), endEventId);
    }

    /**
     * Asserts the process instance with the provided id is ended and has reached <strong>only</strong> the end event
     * with the provided id.
     * 
     * <p>
     * <strong>Note:</strong> this assertion should be used for processes that have exclusive end events and no
     * intermediate end events. This generally only applies to simple processes. If the process definition branches into
     * non-exclusive branches with distinct end events or the process potentially has multiple end events that are
     * reached, this method should not be used.
     * 
     * <p>
     * To test that a process ended in an end event and that particular end event was the final event reached, use
     * {@link #assertProcessEndedAndReachedEndStateLast(String, String)} instead.
     * 
     * <p>
     * To assert that a process ended in an exact set of end events, use
     * {@link #assertProcessEndedAndInEndEvents(String, String...)} instead.
     * 
     * @see #assertProcessEndedAndReachedEndStateLast(String, String)
     * @see #assertProcessEndedAndInEndEvents(String, String...)
     * 
     * @param processInstanceId
     *            the process instance's id to check for. May not be <code>null</code>
     * @param endEventId
     *            the end event's id to check for. May not be <code>null</code>
     */
    public static void assertProcessEndedAndInExclusiveEndEvent(final String processInstanceId, final String endEventId) {
        Validate.notNull(processInstanceId);
        Validate.notNull(endEventId);

        debug(LogMessage.PROCESS_9, processInstanceId, endEventId);
        try {
            getEndEventAssertable().processEndedAndInExclusiveEndEvent(processInstanceId, endEventId);
        } catch (final AssertionError ae) {
            fail(LogMessage.ERROR_PROCESS_3, processInstanceId, endEventId);
        }
    }

    /**
     * Asserts the provided process instance is ended and has reached <strong>all</strong> end events with the provided
     * ids.
     * 
     * <p>
     * <strong>Note:</strong> this assertion assumes the process has one or more end events and that all of them have
     * been reached (in other words, the exact set of provided end event ids is checked against). The order of the end
     * events is not taken into consideration.
     * 
     * @param processInstance
     *            the process instance to check for. May not be <code>null</code>
     * @param endEventIds
     *            the end events' ids to check for. May not be <code>null</code>
     */
    public static final void assertProcessEndedAndInEndEvents(final ProcessInstance processInstance, final String... endEventIds) {
        Validate.notNull(processInstance);
        Validate.notNull(endEventIds);
        assertProcessEndedAndInEndEvents(processInstance.getId(), endEventIds);
    }

    /**
     * Asserts the process instance with the provided id is ended and has reached <strong>all</strong> end events with
     * the provided ids.
     * 
     * <p>
     * <strong>Note:</strong> this assertion assumes the process has one or more end events and that all of them have
     * been reached (in other words, the exact set of provided end event ids is checked against). The order of the end
     * events is not taken into consideration.
     * 
     * @param processInstanceId
     *            the process instance's id to check for. May not be <code>null</code>
     * @param endEventIds
     *            the end events' ids to check for. May not be <code>null</code>
     */
    public static void assertProcessEndedAndInEndEvents(final String processInstanceId, final String... endEventIds) {
        Validate.notNull(processInstanceId);
        Validate.notNull(endEventIds);

        debug(LogMessage.PROCESS_11, processInstanceId, AssertUtils.arrayToString(endEventIds));
        try {
            getEndEventAssertable().processEndedAndInEndEvents(processInstanceId, endEventIds);
        } catch (final AssertionError ae) {
            fail(LogMessage.ERROR_PROCESS_4, processInstanceId, AssertUtils.arrayToString(endEventIds));
        }
    }

    //
    // Assertions for values of process variables
    //

    /**
     * Asserts that the process variable with the provided name is available for the provided process instance and that
     * the <em>latest</em> value of that variable matches the provided expected value.
     * 
     * <p>
     * This assertion places no restrictions on whether the process is active or not. It may be running or may already
     * have been completed; the results of the assertion are not affected by completion of the process.
     * </p>
     * 
     * <p>
     * <strong>Note:</strong> the latest value for the variable is used to check against. This does not imply the
     * variable has not had other values during the execution of the process.
     * </p>
     * 
     * @param processInstance
     *            the process instance to check for. May not be <code>null</code>
     * @param processVariableName
     *            the name of the process variable to check. May not be <code>null</code>
     * @param expectedValue
     *            the expected value for the process variable. May be <code>null</code>
     */
    public static void assertProcessVariableLatestValueEquals(final ProcessInstance processInstance, final String processVariableName,
            final Object expectedValue) {
        Validate.notNull(processInstance);
        Validate.notNull(processVariableName);
        assertProcessVariableLatestValueEquals(processInstance.getId(), processVariableName, expectedValue);
    }

    /**
     * Asserts that the process variable with the provided name is available for the process instance with the provided
     * id and that the <em>latest</em> value of that variable matches the provided expected value.
     * 
     * <p>
     * This assertion places no restrictions on whether the process is active or not. It may be running or may already
     * have been completed; the results of the assertion are not affected by completion of the process.
     * </p>
     * 
     * <p>
     * <strong>Note:</strong> the latest value for the variable is used to check against. This does not imply the
     * variable has not had other values during the execution of the process.
     * </p>
     * 
     * @param processInstanceId
     *            the id of the process instance to check for. May not be <code>null</code>
     * @param processVariableName
     *            the name of the process variable to check. May not be <code>null</code>
     * @param expectedValue
     *            the expected value for the process variable. May be <code>null</code>
     */
    public static void assertProcessVariableLatestValueEquals(final String processInstanceId, final String processVariableName, final Object expectedValue) {
        Validate.notNull(processInstanceId);
        Validate.notNull(processVariableName);
        debug(LogMessage.VARIABLE_3, processVariableName, processInstanceId, expectedValue);
        try {
            getHistoricVariableInstanceAssertable().historicProcessVariableLatestValueEquals(processInstanceId, processVariableName, expectedValue);
        } catch (final AssertionError ae) {
            fail(LogMessage.ERROR_VARIABLE_1, processVariableName, processInstanceId, expectedValue);
        }
    }

    // Marker

    public static final void assertProcessEndedAndReachedEndStateLast(final ProcessInstance processInstance, final String endStateKey) {
        throw new UnsupportedOperationException("This process assertion has not been implemented yet");
    }

    public static final void assertProcessEndedAndReachedEndStateLast(final String processInstanceId, final String endStateKey) {
        throw new UnsupportedOperationException("This process assertion has not been implemented yet");
    }

    private static void initializeConfiguration() {
        if (configuration == null) {
            configuration = new DefaultProcessAssertConfiguration();
        }
    }

    private static ProcessInstanceAssertable getProcessInstanceAssertable() {
        return assertFactory.getProcessInstanceAssertable(getConfiguration());
    }

    private static EndEventAssertable getEndEventAssertable() {
        return assertFactory.getEndEventAssertable(getConfiguration());
    }

    private static TaskInstanceAssertable getTaskInstanceAssertable() {
        return assertFactory.getTaskInstanceAssertable(getConfiguration());
    }

    private static HistoricVariableInstanceAssertable getHistoricVariableInstanceAssertable() {
        return assertFactory.getHistoricVariableInstanceAssertable(getConfiguration());
    }
}
