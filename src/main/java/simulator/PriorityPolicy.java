package simulator;

class PriorityPolicy {
    private static final int NICE_0_VALUE = 1024;
    private static final int[] SCHEDULER_PRIORITY_TO_WEIGHT = {
        88761,     71755,     56483,     46273,     36291,
        29154,     23254,     18705,     14949,     11916,
         9548,      7620,      6100,      4904,      3906,
         3121,      2501,      1991,      1586,      1277,
         1024,       820,       655,       526,       423,
          335,       272,       215,       172,       137,
          110,        87,        70,        56,        45,
           36,        29,        23,        18,        15,
    };

    private static final int MAX_NICE = 19;
    private static final int MIN_NICE = -20;
    private static final int NICE_WIDTH = (MAX_NICE - MIN_NICE + 1);
    private static final int MAX_USER_RT_PRIORITY = 100;
    private static final int MAX_RT_PRIORITY = MAX_USER_RT_PRIORITY;
    private static final int MAX_PRIORITY = (MAX_RT_PRIORITY + NICE_WIDTH);
    private static final int DEFAULT_PRIORITY = (MAX_RT_PRIORITY + NICE_WIDTH / 2);

    /*
    * Convert user-nice values [ -20 ... 0 ... 19 ]
    * to static priority [ MAX_RT_PRIORITY..MAX_PRIORITY-1 ],
    * and back.
    */
    private int niceToPriority(int nice) {
        return nice + DEFAULT_PRIORITY;
    }

    private int prioToNice(int priority) {
        return priority - DEFAULT_PRIORITY;
    }

    /*
    * 'User priority' is the nice value converted to something we
    * can work with better when scaling various scheduler parameters,
    * it's a [ 0 ... 39 ] range.
    */
    private int userPriority(int priority) {
        return priority - MAX_RT_PRIORITY;
    }

    private int maxUserPrio() {
        return userPriority(MAX_PRIORITY);
    }
}
